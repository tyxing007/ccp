package com.chanjet.ccs.base.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chanjet.ccs.base.basic.Constants;
import com.chanjet.ccs.base.basic.ConstantsCcs;
import com.chanjet.ccs.base.bss.BssService;
import com.chanjet.ccs.base.encryption.Cryptos;
import com.chanjet.ccs.base.encryption.Encodes;
import com.chanjet.ccs.base.entity.Result;
import com.chanjet.ccs.base.entity.ResultInner;
import com.chanjet.ccs.base.service.RedisUtil;
import com.chanjet.ccs.ccp.base.basic.EnumResult;

public class AuthHelper {
    
    private static Log logger = LogFactory.getLog(AuthHelper.class);
    
    // 检查authorization，并将解析出的数据存到request.attribute中
    public static Result checkAuth(String authorization, HttpServletRequest request, RedisUtil redisUtil) {
        
        // 请求的时间戳&请求IP
        String ipAndTime = request.getRemoteAddr() + Constants.DOUB_COLON + System.currentTimeMillis();
        request.setAttribute(ConstantsCcs.kReqIpAndTime, ipAndTime);
        
        Map<String, String> paramsReturn = new HashMap<String, String>(); // 放置已经检查过的参数
        
        if (StringUtils.isEmpty(authorization))
            return EnumResult.getResult(EnumResult.uniLostParams);
        
        // base64解码
        byte[] bytes = Encodes.decodeBase64(authorization);
        try {
            authorization = new String(bytes, Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            logger.error("解码authorization失败", e);
            return EnumResult.getResult(EnumResult.auAuthorInvalid);
        }
        
        // 记录原始请求
        logger.info(ipAndTime + Constants.DOUB_COLON + authorization);
        
        Map<String, String> params = DataWraper.wrapJson2MapStr(authorization);
        if (params.size() != 3)
            return EnumResult.getResult(EnumResult.auAuthorIrregular);
        
        // 通用请求头
        String bssAppKey = StringUtils.trimToEmpty(params.get(ConstantsCcs.kAppKey));
        String paramInfo = StringUtils.trimToEmpty(params.get(ConstantsCcs.kParamInfo));
        String authInfo = StringUtils.trimToEmpty(params.get(ConstantsCcs.kAuthInfo));
        
        
        // TODO 看能否能数据库中获取/缓存中获取
        // 根据appKey从Bss获取appId、appSecret
        String bssAppInCache = ConstantsCcs.cacheAppPre + bssAppKey;
        HashMap<String, String> bssAppCertification = null;
        bssAppCertification = (HashMap<String, String>)(redisUtil.getObjSerial(bssAppInCache));
        if (bssAppCertification == null) {
            ResultInner<HashMap<String, String>> appResult = BssService.getAppCertification(bssAppKey);
            if (!appResult.getResult())
                return EnumResult.getResult(appResult.getResultKey());
            bssAppCertification = appResult.getData();
            redisUtil.setObjSerial(bssAppInCache, bssAppCertification);
        }
        //Map<String, String> bssAppCertification = appResult.getData();
        String bssAppSecret = bssAppCertification.get(ConstantsCcs.kBssAppSecret);
        String bssAppId = bssAppCertification.get(ConstantsCcs.kBssAppId);
        String bssIsvId = bssAppCertification.get(ConstantsCcs.kBssIsvId);
        
        if (StringUtils.isEmpty(bssAppSecret))
            return EnumResult.getResult(EnumResult.auAccessKeyInvalid);
        
        // 从params中获取参数
        Map<String, String> baseParams = DataWraper.wrapJson2MapStr(paramInfo);
        if (baseParams.size() < 2) // TODO ?
            return EnumResult.getResult(EnumResult.auAuthorIrregular); // 必须参数：referer\date
        
        String referer = StringUtils.trimToEmpty(baseParams.get(ConstantsCcs.kReferer));
        String date = StringUtils.trimToEmpty(baseParams.get(ConstantsCcs.kDate));
        
        // 验证referer
        if (StringUtils.isEmpty(referer))
            return EnumResult.getResult(EnumResult.uniLostParams); // TODO 要求
        
        // 验证时间
        if (checkRequestDate(date) != null)
            return EnumResult.getResult(EnumResult.uniTimeError);
        
        // 下列参数非必需传递---如果不传递从企业账户中扣钱
        String bssAccessToken = StringUtils.trimToEmpty(baseParams.get(ConstantsCcs.kToken)); 
        String bssOrgAccessToken = StringUtils.trimToEmpty(baseParams.get(ConstantsCcs.kOrgToken));
        String reqBssAppId = StringUtils.trimToEmpty(baseParams.get(ConstantsCcs.kAppId));
        String reqBssOrgId = StringUtils.trimToEmpty(baseParams.get(ConstantsCcs.kOrgId));
        String reqBssUserId = StringUtils.trimToEmpty(baseParams.get(ConstantsCcs.kUserId));
        
        // 验证bssAccessToken
        if (!StringUtils.isEmpty(bssAccessToken)) {
            
            // TODO 验证bssOrgToken---做什么用的？
            if (StringUtils.isEmpty(bssOrgAccessToken)) { }
            
            // TODO 看能否能数据库中获取/缓存中获取
            // 带着token到Bss取用户信息
            String bssUserInCache = ConstantsCcs.cacheAppPre + bssAccessToken;
            HashMap<String, String> bssUserInfo = null;
            bssUserInfo = (HashMap<String, String>)(redisUtil.getObjSerial(bssUserInCache));
            if (bssUserInfo == null) {
                ResultInner<HashMap<String, String>> userResult = BssService.getUserInfo(bssAccessToken);
                if (!userResult.getResult())
                    return EnumResult.getResult(userResult.getResultKey());
                bssUserInfo = userResult.getData();
                redisUtil.setObjSerial(bssUserInCache, bssUserInfo);
            }
            
//            ResultInner<Map<String, String>> userResult = BssService.getUserInfo(bssAccessToken);
//            if (!userResult.getResult())
//                return EnumResult.getResult(userResult.getResultKey());
//            Map<String, String> bssUserInfo = userResult.getData();
            String bssOrgId = bssUserInfo.get(ConstantsCcs.kBssOrgId);
            String bssUserId = bssUserInfo.get(ConstantsCcs.kBssUserId);
            
            // 如果传递了appId，需要与Bss上获取到的appId一致
            //if (StringUtils.isNotEmpty(reqBssAppId) && !reqBssAppId.equals(bssAppId))
            //    return EnumResult.getResult(EnumResult.uniParamsInvalid);
            
            // 如果传递了orgId，需要与Bss上获取到的orgId一致
            if (StringUtils.isNotEmpty(reqBssOrgId) && !reqBssOrgId.equals(bssOrgId))
                return EnumResult.getResult(EnumResult.uniParamsInvalid);
            
            // 如果传递了userId，需要与Bss上获取到的userId一致
            if (StringUtils.isNotEmpty(reqBssUserId) && !reqBssUserId.equals(bssUserId))
                return EnumResult.getResult(EnumResult.uniParamsInvalid);
            
            paramsReturn.put(ConstantsCcs.kBssAccessToken, bssAccessToken);
            paramsReturn.put(ConstantsCcs.kBssOrgAccessToken, bssOrgAccessToken);
            paramsReturn.put(ConstantsCcs.kBssOrgId, bssOrgId);
            paramsReturn.put(ConstantsCcs.kBssUserId, bssUserId);
            
        }
        
        // 验证加密的参数串
        boolean check = checkSign(paramInfo, authInfo, bssAppSecret);
        if (!check)
            return EnumResult.getResult(EnumResult.auAccessDenied);
        
        
        paramsReturn.put(ConstantsCcs.kBssAppId, bssAppId);
        paramsReturn.put(ConstantsCcs.kBssAppKey, bssAppKey);
        paramsReturn.put(ConstantsCcs.kBssIsvId, bssIsvId);
        paramsReturn.put(ConstantsCcs.kBssAppSecret, bssAppSecret);
        ReqRespHelper.setAttrsToReq(request, paramsReturn);
        
        return null;
    }
    
    
    // 验证请求时间 : yyyy-MM-dd HH:mm:ss
    public static Result checkRequestDate(String dateStr) {
        Result result = null;
        try {
            if (StringUtils.isEmpty(dateStr))
                return EnumResult.getResult(EnumResult.uniLostParams);
            
            Date date = DateUtil.strToAllDate(dateStr);
            if (date == null)
                return EnumResult.getResult(EnumResult.uniTimeParseError);
            
            // 验证用户时间戳
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            long userTime = cal.getTimeInMillis();
            long sysTime = System.currentTimeMillis();
            if (sysTime - userTime > ConstantsCcs.vReqExpire || userTime - sysTime > ConstantsCcs.vReqExpire)
                result =  EnumResult.getResult(EnumResult.uniTimeError);
        } catch (Exception e) {
            logger.info("解析时间出错", e);
            result = EnumResult.getResult(EnumResult.uniTimeParseError);
        }
        return result;
    }
    
    
    // 验证加密的参数串
    public static boolean checkSign(String paramInfo, String authInfo, String accessSecret) {
        try {
            byte[] encodeSignature = Encodes.decodeHex(authInfo);
            byte[] actual = Cryptos.hmacSha1(paramInfo.getBytes(Constants.UTF8), accessSecret.getBytes());
            return Arrays.equals(encodeSignature, actual);
        } catch (UnsupportedEncodingException e) {
            logger.error("验证签名报错", e);
        } catch (Exception e) {
            logger.error("验证签名报错", e);
        }
        return false;
    }
}
