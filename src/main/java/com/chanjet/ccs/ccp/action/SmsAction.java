package com.chanjet.ccs.ccp.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chanjet.ccs.base.basic.ConstantsCcs;
import com.chanjet.ccs.base.entity.App;
import com.chanjet.ccs.base.entity.Result;
import com.chanjet.ccs.base.entity.ResultInner;
import com.chanjet.ccs.base.entity.User;
import com.chanjet.ccs.base.service.CcsService;
import com.chanjet.ccs.base.service.RedisUtil;
import com.chanjet.ccs.base.util.CommonUtil;
import com.chanjet.ccs.base.util.ReqRespHelper;
import com.chanjet.ccs.ccp.activemq.MessageSender;
import com.chanjet.ccs.ccp.base.basic.ConstantsCcp;
import com.chanjet.ccs.ccp.base.basic.EnumResult;
import com.chanjet.ccs.ccp.base.basic.EnumValues;
import com.chanjet.ccs.ccp.base.entity.ExcodeUserRecord;
import com.chanjet.ccs.ccp.base.entity.ResultDataSms;
import com.chanjet.ccs.ccp.base.entity.ResultDataSmsStatus;
import com.chanjet.ccs.ccp.base.entity.Sms;
import com.chanjet.ccs.ccp.base.entity.VoiceSms;
import com.chanjet.ccs.ccp.base.service.VoiceService;
import com.chanjet.ccs.ccp.base.util.DFAFilter;
import com.chanjet.ccs.ccp.service.CcpService;
import com.chanjet.ccs.common.sdk.base.CcsCMResult;
import com.chanjet.ccs.common.sdk.base.CcsCMService;
import com.chanjet.ccs.common.sdk.entity.CcsCMDeduct;
import com.chanjet.ccs.common.sdk.service.CcsCMUtil;
import com.chanjet.ccs.common.sdk.util.DataWraper;

@Controller
@RequestMapping(value = ConstantsCcp.urlSms)
public class SmsAction {
    
    private static Log logger = LogFactory.getLog(SmsAction.class);
    
    @Resource
    private CcpService ccpService;
    @Resource
    private VoiceService voiceService;
    @Resource
    private CcsService ccsService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private MessageSender sender;
    
    // 发送系统类短信
    @RequestMapping(value = ConstantsCcp.urlSmsSystem, method = RequestMethod.POST)
    @ResponseBody
    public Object sendSystemSms(HttpServletRequest request, HttpServletResponse res) {
        return _send(ReqRespHelper.getAttrsFromReq(request));
    }
    
    // 发送用户类短信（可上行）
    @RequestMapping(value = ConstantsCcp.urlSmsUser, method = RequestMethod.POST)
    @ResponseBody
    public Object sendUserSms(HttpServletRequest request, HttpServletResponse response) {
        return _send(ReqRespHelper.getAttrsFromReq(request));
    }
    
    // 发送通知类
    @RequestMapping(value = ConstantsCcp.urlSmsNotice, method = RequestMethod.POST)
    @ResponseBody
    public Object sendBussinessSms(HttpServletRequest request, HttpServletResponse response) {
        return _send(ReqRespHelper.getAttrsFromReq(request));
    }
    
    // 发送营销类
    @RequestMapping(value = ConstantsCcp.urlSmsMarket, method = RequestMethod.POST)
    @ResponseBody
    public Object sendMarketSms(HttpServletRequest request, HttpServletResponse response) {
        return _send(ReqRespHelper.getAttrsFromReq(request));
    }
    
    // 发送语音类
    @RequestMapping(value = ConstantsCcp.urlSmsAudio, method = RequestMethod.POST)
    @ResponseBody
    public Object sendAudioSms(HttpServletRequest request, HttpServletResponse response) {
    	return _sendAudio(ReqRespHelper.getAttrsFromReq(request));
    }
    
    /**
	 * @param attrsFromReq
	 * @return
	 */
	private Object _sendAudio(Map<String, Object> params) {
		VoiceSms voiceSms = new VoiceSms();
        CommonUtil.getBean(voiceSms, params);
        if(voiceSms == null || voiceSms.getContent() == null || "".equals(voiceSms.getContent())
        		||voiceSms.getMobiles() == null || "".equals(voiceSms.getMobiles())){
        	return EnumResult.getResult(EnumResult.uniLostParams);
		}
        if(!voiceSms.getContent().matches("\\d{4,6}")){
        	return EnumResult.getResult(EnumResult.uniParamsInvalid);
        }
        if(!voiceSms.getMobiles().matches("\\d{11}")){
        	return EnumResult.getResult(EnumResult.uniParamsInvalid);
        }
        voiceSms.setCreateTime(new Date());
        if(voiceSms.getTimingTime() == null) {
            try {
            	voiceSms.setState(EnumValues.SMS_UNSEND);
            	voiceService.saveAndSend(voiceSms);
            } catch (Exception e) {
                logger.error("保存消息发送状态或连接语音通道失败,smsId = " + voiceSms.getId(), e);
                return EnumResult.getResult(EnumResult.dbOpeOccurError);
            }
        }
        ResultDataSms resultData = new ResultDataSms();
        resultData.setSmsId(voiceSms.getId()+"");
        return EnumResult.modifyInfo(EnumResult.uniSuccess, resultData.toString());
	}

	@RequestMapping(value = ConstantsCcp.urlSmsStatus, method = RequestMethod.GET)
    @ResponseBody
    public Object getSmsState(@PathVariable String smsId, HttpServletRequest request, HttpServletResponse response) {
        int id = NumberUtils.toInt(smsId);
        if (id <= 0)
            return EnumResult.getResult(EnumResult.uniParamsInvalid);
        
        ResultInner<Sms> resultSms = ccpService.findSms(id);
        if (!resultSms.getResult())
            return EnumResult.getResult(resultSms.getResultKey());
        
        Sms sms = resultSms.getData();
        if (sms.getState() == EnumValues.SMS_SENDED || sms.getState() == EnumValues.SMS_FAILED) {
            sms.setSyncFlag(EnumValues.SYNC_FLAG_SYNCED);
            ccpService.save(sms);
        }
        return EnumResult.modifyInfo(EnumResult.uniSuccess, ResultDataSmsStatus.getStatus(sms.getState()));
    }
    
    
    // 检查黑关键字    返回黑关键字
    @RequestMapping(value = ConstantsCcp.urlSmsCheck, method = RequestMethod.GET)
    @ResponseBody
    public Object checkBlackKeys(@PathVariable String smsType, HttpServletRequest request, HttpServletResponse response) {
        
        String content = StringUtils.trimToEmpty(request.getParameter(ConstantsCcp.kContent));
        logger.info(content);
/*        try {
            content = new String(content.getBytes(Constants.ISO8859), Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            logger.error("解码content失败");
            return EnumResult.getResult(EnumResult.sysOccurError);
        }*/
        if (StringUtils.isEmpty(content))
            return EnumResult.getResult(EnumResult.uniLostParams);
        
        String service = null;
        int level;
        if (ConstantsCcp.smsSystem.equals(smsType) || ConstantsCcp.smsUser.equals(smsType)) {
            service = ConstantsCcs.SERV_UPSMS;
            level = EnumValues.LEVEL_THIRD;
        } else if (ConstantsCcp.smsNotice.equals(smsType) || ConstantsCcp.smsMarket.equals(smsType)) {
            service = ConstantsCcs.SERV_SMS;
            level = EnumValues.LEVEL_FIRST;
        } else
            return EnumResult.getResult(EnumResult.uniUnvalidUrl);
        
        String bssAppKey = (String)request.getAttribute(ConstantsCcs.kBssAppKey);
        String bssOrgId = (String)request.getAttribute(ConstantsCcs.kBssOrgId);
        String bssUserId = (String)request.getAttribute(ConstantsCcs.kBssUserId);
        
        
        if (StringUtils.isNotEmpty(bssOrgId) && StringUtils.isNotEmpty(bssUserId)) {
            // 查询用户
            ResultInner<User> resultUser = this._findUser(bssAppKey, bssOrgId, bssUserId, service);
            if (!resultUser.getResult())
                return EnumResult.getResult(resultUser.getResultKey());
            User user = resultUser.getData();
            level = user.getLevel();
        }
        
        // 根据级别检查黑关键字
        List<String> blackKeys = DFAFilter.filterBlackKey(level, content);
        if(blackKeys.size() > 0)
            return EnumResult.appendInfo(EnumResult.smsHasBlackKeys, blackKeys.toString());
        
        return EnumResult.modifyInfo(EnumResult.uniSuccess, "没有黑关键字");
            
    }
    
    
    // 获取余额
    @RequestMapping(value = ConstantsCcp.urlAmount, method = RequestMethod.GET)
    @ResponseBody
    public Object getAmount(@PathVariable String smsType, HttpServletRequest request, HttpServletResponse response) {
        String ccsSubServiceID = null;
        if (ConstantsCcp.smsSystem.equals(smsType) || ConstantsCcp.smsUser.equals(smsType))
            ccsSubServiceID = String.valueOf(CcsCMService.subService.ccp_sendAndRevice);
        else if (ConstantsCcp.smsNotice.equals(smsType))
            ccsSubServiceID = String.valueOf(CcsCMService.subService.ccp_send_notice);
        else if (ConstantsCcp.smsMarket.equals(smsType))
            ccsSubServiceID = String.valueOf(CcsCMService.subService.ccp_send_Marketing);
        else
            return EnumResult.getResult(EnumResult.uniUnvalidUrl);
        String bssOrgId = (String)request.getAttribute(ConstantsCcs.kBssOrgId);
        try {
            String ccsId = String.valueOf(CcsCMService.service.ccp);
            CcsCMResult resultCheck = CcsCMUtil.getServiceAmount(ConstantsCcs.vAppKey, ConstantsCcs.vAppSecret, ccsId, ccsSubServiceID, bssOrgId);
            if (resultCheck == null) {
                logger.error("获取余额失败, bssOrgId = " + bssOrgId);
                return EnumResult.getResult(EnumResult.commonAccessError);
            }
//            if (resultCheck.getHttpCode() == 200) {
//                ObjectMapper mapper = new ObjectMapper(); 
//                List<?> list = mapper.readValue(resultCheck.getInfo(), List.class);
//                Map map = (Map)list.get(0);
//            }
            response.setStatus(resultCheck.getHttpCode());
            return resultCheck;
        } catch (Exception ex) {
            logger.error("获取余额失败", ex);
        }
        return EnumResult.getResult(EnumResult.sysOccurError); // TODO 调整此错误信息
    }
    
    
    // TODO 拆分
    private Result _send(Map<String, Object> params) {
        Sms sms = new Sms();
        CommonUtil.getBean(sms, params);
        sms.setCreateTime(new Date());
        String ccsSubServiceID = String.valueOf(CcsCMService.subService.ccp_sendAndRevice);
        int level = EnumValues.LEVEL_THIRD;
        if (sms.getSmsType() == EnumValues.SMS_TYPE_NOTICE) {
            ccsSubServiceID = String.valueOf(CcsCMService.subService.ccp_send_notice);
            level = EnumValues.LEVEL_FIRST;
        } else if (sms.getSmsType() == EnumValues.SMS_TYPE_MARKET) {
            ccsSubServiceID = String.valueOf(CcsCMService.subService.ccp_send_Marketing);
            level = EnumValues.LEVEL_FIRST;
        } else {
            // 查询应用验证相关设置
            ResultInner<App> resultApp = ccsService.findByAppKey(sms.getBssAppKey());
            if (resultApp.getResultKey().equals(EnumResult.dbNotFindEntity)) {
                App app = new App();
                app.setBssAppId(sms.getBssAppId());
                app.setBssAppKey(sms.getBssAppKey());
                app.setBssAppSecret(sms.getBssAppSecret());
                app.setBssIsvId((String)params.get(ConstantsCcs.kBssIsvId));
                app.setStatus(EnumValues.STATUS_ENABLE);
                app.setExcodeType(EnumValues.EXCODE_STATIC);
                app.setWhoseExcode(EnumValues.EXCODE_APP_USER);
                app.setCreateDateTime(new Date());
                app.setUpdateDateTime(new Date());
                resultApp = ccsService.save(app);
            }
            if (!resultApp.getResult())
                return EnumResult.getResult(resultApp.getResultKey());
            App app = resultApp.getData();
            // 应用不可用
            if (app.getStatus() == EnumValues.STATUS_DISABLE)
                return EnumResult.getResult(EnumResult.appServNOtActive);
            boolean hasToken = (params.get(ConstantsCcs.kBssAccessToken) != null);
            ResultInner<String> resultCode = this._checkExcode(app, sms, hasToken);
            if (!resultCode.getResult())
                return EnumResult.getResult(resultCode.getResultKey());
        }
        sms.setLevel(level);
        sms.setState(EnumValues.SMS_UNSEND);
        if (StringUtils.isNotEmpty(sms.getBssAccessToken())) {
            // 查询用户
            ResultInner<User> resultUser = this._findUser(sms.getBssAppKey(), sms.getBssOrgId(), sms.getBssUserId(), ccsSubServiceID);
            if (!resultUser.getResult())
                return EnumResult.getResult(resultUser.getResultKey());
            User user = resultUser.getData();
            sms.setLevel(user.getLevel()); // 短信级别设置为用户级别
        } else {
            ResultInner<User> resultIsv = this._findIsv(sms.getBssAppKey(), (String)params.get(ConstantsCcs.kBssIsvId), ccsSubServiceID);
            if (!resultIsv.getResult())
                return EnumResult.getResult(resultIsv.getResultKey());
            User user = resultIsv.getData();
            sms.setLevel(user.getLevel()); // 短信级别设置为用户级别
        }
        // 根据级别检查黑关键字
        List<String> blackKeys = DFAFilter.filterBlackKey(sms.getLevel(), sms.getContent());
        if(blackKeys.size() > 0)
            return EnumResult.appendInfo(EnumResult.smsHasBlackKeys, blackKeys.toString());
        CcsCMDeduct deduct = new CcsCMDeduct();
        deduct.setBssAppId(sms.getBssAppId());
        if (params.containsKey(ConstantsCcs.kBssAccessToken))
            deduct.setBssOrgId(sms.getBssOrgId());
        else
            deduct.setBssOrgId((String)params.get(ConstantsCcs.kBssIsvId));
        deduct.setCcsID(String.valueOf(CcsCMService.service.ccp));
        deduct.setCcsSubServiceID(String.valueOf(ccsSubServiceID));
        deduct.setAmount(sms.getBillingCount());
        deduct.setDimensions(String.valueOf(CcsCMService.dimensions.member));
        sms.setBssOrgId(deduct.getBssOrgId()); // 扣费的企业
        logger.info(deduct.toString());
        CcsCMResult resultCheck = CcsCMUtil.deduct(ConstantsCcs.vAppKey, ConstantsCcs.vAppSecret, deduct);
        if (resultCheck == null) {
            logger.error("扣费失败, smsMobiles = " + sms.getMobiles());
            return EnumResult.getResult(EnumResult.commonAccessError);
        }
        if (resultCheck.getHttpCode() != 200) 
            return new Result(resultCheck.getHttpCode(), resultCheck.getCode(), resultCheck.getInfo());
        String deductNo = DataWraper.wrapJson2MapStr(resultCheck.getInfo()).get("deductNo");
        sms.setBillingNo(deductNo);
        String amtInCache = ConstantsCcs.cacheCcpAmtPre + deduct.getCcsID() + ccsSubServiceID + deduct.getBssOrgId();
        redisUtil.delete(amtInCache);
        ResultInner<Sms> resultSms = ccpService.save(sms);
        if (!resultSms.getResult()) {
            resultCheck = CcsCMUtil.robackDeduct(ConstantsCcs.vAppKey, ConstantsCcs.vAppSecret, 
                    deductNo, String.valueOf(sms.getBillingCount()));
            if (resultCheck == null) {
                logger.error("回滚费用失败, deductNo = " + deductNo);
                return EnumResult.getResult(EnumResult.commonAccessError);
            }
            if (resultCheck.getHttpCode() != 200) 
                logger.error("回滚费用失败, deductNo = " + deductNo);
            return EnumResult.getResult(resultSms.getResultKey());
        }
        if(sms.getTimingTime() == null) {
            try {
                sms.setState(EnumValues.SMS_SENDING);
                ccpService.save(sms);
                sender.sendMessage(String.valueOf(sms.getId()));
                logger.info("sended to mq , id = " + sms.getId());
            } catch (Exception e) {
                logger.error("保存消息发送状态，或者向消息中间件转发消息过程中出错，smsId = " + sms.getId(), e);
            }
        }
        ResultDataSms resultData = new ResultDataSms(sms.getId(), sms.getBillingNo(), sms.getBillingCount());
        return EnumResult.modifyInfo(EnumResult.uniSuccess, resultData.toString());
    }
    
 
    // 查询用户
    private ResultInner<User> _findUser(String bssAppKey, String bssOrgId, String bssUserId, String service) {
        ResultInner<User> resultUser = ccsService.findUser(bssAppKey, bssOrgId, bssUserId, service);
        if (resultUser.getResult()) return resultUser;
        if (resultUser.getResultKey().equals(EnumResult.dbNotFindEntity)) {
            User user = new User();
            user.setBssAppKey(bssAppKey);
            user.setBssOrgId(bssOrgId);
            user.setBssUserId(bssUserId);
            user.setService(service);
            user.setLevel(ConstantsCcs.defaultLevel);
            if (CcsCMService.subService.ccp_sendAndRevice.toString().equals(service))
                user.setLevel(ConstantsCcs.srLevel);
            user.setState(EnumValues.STATUS_ENABLE);
            user.setCreateTime(new Date());
            ccsService.save(user);
            return new ResultInner<User>(EnumResult.uniSuccess, user);
        }
        return resultUser;
    }
    
    private ResultInner<User> _findIsv(String bssAppKey, String bssIsvId, String service) {
        ResultInner<User> resultIsv = ccsService.findIsv(bssAppKey, bssIsvId, service);
        
        if (resultIsv.getResult())
            return resultIsv;
        
        if (resultIsv.getResultKey().equals(EnumResult.dbNotFindEntity)) {
            User isv = new User();
            isv.setBssAppKey(bssAppKey);
            isv.setBssUserId(bssIsvId);
            isv.setService(service);
            isv.setLevel(ConstantsCcs.defaultLevel);
            if (CcsCMService.subService.ccp_sendAndRevice.toString().equals(service))
                isv.setLevel(ConstantsCcs.srLevel);
            isv.setState(EnumValues.STATUS_ENABLE);
            isv.setCreateTime(new Date());
            ccsService.save(isv);
            return new ResultInner<User>(EnumResult.uniSuccess, isv);
        }
        return resultIsv;
    }
    
    
    // 获取扩展号
    private ResultInner<String> _checkExcode(App app, Sms sms, boolean hasToken) {
        int excodeType = app.getExcodeType();
       
        if (excodeType == EnumValues.EXCODE_STATIC) {
            ResultInner<ExcodeUserRecord> result = null;
            if (!hasToken)
                result = ccpService.findEur(excodeType, sms.getBusiness(), sms.getBssAppKey(), "", "");
            else if (app.getWhoseExcode() == EnumValues.EXCODE_APP)
                result = ccpService.findEur(excodeType, sms.getBusiness(), sms.getBssAppKey(), "", "");
            else if (app.getWhoseExcode() == EnumValues.EXCODE_APP_ORG)
                result = ccpService.findEur(excodeType, sms.getBusiness(), sms.getBssAppKey(), sms.getBssOrgId(), "");
            else if (app.getWhoseExcode() == EnumValues.EXCODE_APP_USER)
                result = ccpService.findEur(excodeType, sms.getBusiness(), sms.getBssAppKey(), sms.getBssOrgId(), sms.getBssUserId());

            if(result == null){
            	return new ResultInner<String>(EnumResult.sysOccurError);
            }
            if (result.getResult()) {
                ExcodeUserRecord eur = result.getData();
                sms.setExtendCode(eur.getCodeName());
                return new ResultInner<String>(true);
            }
            if (EnumResult.dbNotFindEntity.equals(result.getResultKey()))
                return new ResultInner<String>(EnumResult.excodeNeeds); // TODO 应300重定向
            return new ResultInner<String>(result.getResultKey());
            
        } else if (excodeType == EnumValues.EXCODE_DYNAMIC) { // TODO 尚未支持动态分配
            
            if (app.getWhoseExcode() == EnumValues.EXCODE_APP) {
                
            } else if (app.getWhoseExcode() == EnumValues.EXCODE_APP_ORG) {
                
            } else if (app.getWhoseExcode() == EnumValues.EXCODE_APP_USER) {
                
            }
        }
        return null;
    }
    
}
