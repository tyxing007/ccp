package com.chanjet.ccs.ccp.base.interceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.chanjet.ccs.base.basic.Constants;
import com.chanjet.ccs.base.basic.ConstantsCcs;
import com.chanjet.ccs.base.entity.Result;
import com.chanjet.ccs.base.util.DateUtil;
import com.chanjet.ccs.base.util.ReqRespHelper;
import com.chanjet.ccs.ccp.base.basic.ConstantsCcp;
import com.chanjet.ccs.ccp.base.basic.EnumResult;
import com.chanjet.ccs.ccp.base.basic.EnumValues;
import com.chanjet.ccs.ccp.base.util.CcpAccessLog;

// 检查短信发送请求参数
public class SmsParamsInterceptor extends HandlerInterceptorAdapter {

    private static Log logger = LogFactory.getLog(SmsParamsInterceptor.class);
    private static final Log accessLog = LogFactory.getLog(CcpAccessLog.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        Map<String, String> params = ReqRespHelper.getParamsFromReq(request); 
        String url = request.getRequestURI();
        if (url.startsWith(ConstantsCcp.urlSms)) {
            if (url.equals(ConstantsCcp.urlSms + ConstantsCcp.urlSmsSystem) || 
                    url.equals(ConstantsCcp.urlSms + ConstantsCcp.urlSmsUser) ||
                    url.equals(ConstantsCcp.urlSms + ConstantsCcp.urlSmsNotice) ||
                    url.equals(ConstantsCcp.urlSms + ConstantsCcp.urlSmsMarket) || 
                    url.equals(ConstantsCcp.urlSms + ConstantsCcp.urlSmsAudio)) {
                String content = StringUtils.trimToEmpty(params.get(ConstantsCcp.kContent));
                String mobiles = StringUtils.trimToEmpty(params.get(ConstantsCcp.kMobiles));
                String timingTime = StringUtils.trimToEmpty(params.get(ConstantsCcp.kTimingTime));
                
                logger.info(content);
                
                // 不能为空
                if (StringUtils.isEmpty(content) || StringUtils.isEmpty(mobiles))
                    return ReqRespHelper.setAndWriteResult(response, EnumResult.getResult(EnumResult.uniLostParams));
                
                // 短信类型
                int smsType = EnumValues.SMS_TYPE_SYSTEM;
                if (url.endsWith(ConstantsCcp.urlSmsUser))
                    smsType = EnumValues.SMS_TYPE_USER;
                else if (url.endsWith(ConstantsCcp.urlSmsNotice))
                    smsType = EnumValues.SMS_TYPE_NOTICE;
                else if (url.endsWith(ConstantsCcp.urlSmsMarket))
                    smsType = EnumValues.SMS_TYPE_MARKET;
                
                // 可上行短信的签名检查
                if ((smsType == EnumValues.SMS_TYPE_SYSTEM || smsType == EnumValues.SMS_TYPE_USER) && 
                        (content.lastIndexOf(ConstantsCcp.vSignPrefix) == -1 || !content.endsWith(ConstantsCcp.vSignSuffix)) &&!url.equals(ConstantsCcp.urlSms + ConstantsCcp.urlSmsAudio))
                	content += ConstantsCcp.vSignUPContent;
                // 普通短信的签名检查
                if ((smsType == EnumValues.SMS_TYPE_NOTICE || smsType == EnumValues.SMS_TYPE_MARKET) && 
                        (content.lastIndexOf(ConstantsCcp.vSignPrefix) == -1 || !content.endsWith(ConstantsCcp.vSignSuffix)))
                    content += ConstantsCcp.vSignContent;
                // 内容长度检查
                if (content.length() > ConstantsCcp.vContentLength)
                    return ReqRespHelper.setAndWriteResult(response, EnumResult.getResult(EnumResult.smsContentTooLarge));
                
                // 接收用户数量检查
                StringTokenizer st = new StringTokenizer(mobiles, Constants.COMMA);
                int count = st.countTokens();
                if(count <= 0 || count > ConstantsCcp.vMobilesNum)
                    return ReqRespHelper.setAndWriteResult(response, EnumResult.getResult(EnumResult.smsMobilesTooMany));
                
                // 检查手机号格式
                List<String> errorMobiles = new ArrayList<String>();
                while(st.hasMoreElements()) {
                    String mobile = st.nextToken();
                    if(!checkMobile(mobile))
                        errorMobiles.add(mobile);
                }
                if(errorMobiles.size() > 0) {
                    Result result = EnumResult.appendInfo(EnumResult.uniParamsInvalid, errorMobiles.toString());
                    return ReqRespHelper.setAndWriteResult(response, result);
                    
                }
                
                // 解析\检查定时发送的日期
                Date sendDT = null;
                if (StringUtils.isNotEmpty(timingTime)) {
                    sendDT = DateUtil.strToAllDate(timingTime);
                    Date now = new Date();
                    now.setMinutes(now.getMinutes() - ConstantsCcp.vTimingTimeBuffer);
                    if (sendDT == null)
                        return ReqRespHelper.setAndWriteResult(response, EnumResult.getResult(EnumResult.uniTimeParseError));
                    else if (sendDT.before(now))
                        return ReqRespHelper.setAndWriteResult(response, EnumResult.getResult(EnumResult.uniTimeError));
                }
                
                int length = content.length() + ConstantsCcp.vContentLengthPerSms - 1;
                int splitCount = length / ConstantsCcp.vContentLengthPerSms;
                
                request.setAttribute(ConstantsCcp.kContent, content);
                request.setAttribute(ConstantsCcp.kMobiles, mobiles);
                request.setAttribute(ConstantsCcp.kTimingTime, sendDT);
                request.setAttribute(ConstantsCcp.kCount, count); // 发送人数
                request.setAttribute(ConstantsCcp.kSplitCount, splitCount); // 单条短信被拆分的数量
                request.setAttribute(ConstantsCcp.kBillingCount, splitCount * count); // 拆分后短信总数量
                request.setAttribute(ConstantsCcp.kSmsType, smsType); // 短信类型
                request.setAttribute(ConstantsCcp.kState, EnumValues.SMS_UNSEND);
                
            } else if (url.endsWith("amount")){
                if (request.getAttribute(ConstantsCcs.kBssAccessToken) == null)
                    request.setAttribute(ConstantsCcs.kBssOrgId, request.getAttribute(ConstantsCcs.kBssIsvId));
            } else {
                
            }
        }
//        else if (url.startsWith(ConstantsCcp.urlExcode)) {
//            
//        } else if (url.startsWith(ConstantsCcp.urlDeliver)) {
//            
//        }
        
        // TODO  记录请求日志！
        String logInfo = getAccessLog(request);
        accessLog.info(logInfo);
        return true;
    }
    
    
    //手机格式检查，只检查11位即可
    private static boolean checkMobile(String mobile) {
        Pattern p1 = Pattern.compile("\\d{11}");
        Matcher m1 = p1.matcher(mobile);
        return m1.matches();
    }

    private String getAccessLog(HttpServletRequest request){
    	String url = request.getRequestURI();// 请求地址
    	String isvId = (String)request.getAttribute(ConstantsCcs.kBssIsvId);
    	String appId = (String)request.getAttribute(ConstantsCcs.kBssAppId);
    	String accessToken = (String)request.getAttribute(ConstantsCcs.kBssAccessToken);
    	String orgId = (String)request.getAttribute(ConstantsCcs.kBssOrgId);
    	String userId = (String)request.getAttribute(ConstantsCcs.kBssUserId);
    	
    	StringBuilder logInfoBuilder = new StringBuilder();
    	logInfoBuilder.append(StringUtils.isNotBlank(url) ? url : "*");
    	logInfoBuilder.append(" ");
    	logInfoBuilder.append(StringUtils.isBlank(isvId) ? "*" : isvId);
    	logInfoBuilder.append(" ");
    	logInfoBuilder.append(StringUtils.isBlank(appId) ? "*" : appId);    		
    	logInfoBuilder.append(" ");
    	logInfoBuilder.append(StringUtils.isBlank(accessToken) ? "*" : accessToken);
    	logInfoBuilder.append(" ");
    	logInfoBuilder.append(StringUtils.isBlank(orgId) ? "*" : orgId);
    	logInfoBuilder.append(" ");
    	logInfoBuilder.append(StringUtils.isBlank(userId) ? "*" : userId);
		
		return logInfoBuilder.toString();
    }

}
