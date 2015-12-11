package com.chanjet.ccs.ccp.base.basic;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import com.chanjet.ccs.base.basic.Constants;
import com.chanjet.ccs.base.entity.Result;


public class EnumResult {
    
    
    // 通用信息
    public static final String uniSuccess = "200::Success::请求成功";
    public static final String uniLostParams = "400::LostParams::缺少参数";
    public static final String uniParamsInvalid = "400::ParamsInvalid::无效的参数";
    public static final String uniTimeParseError = "400::TimeParseError::解析时间出错";
    public static final String uniTimeError = "400::TimeError::无效/失效的时间";
    public static final String uniUnvalidUrl = "404::UnvalidUrl::无效的请求地址";
    public static final String uniParseJsonError = "404::ParseJsonError::解析JSON串出错";
    public static final String uniParseBizDataError = "400::ParseBizDataError::解析业务数据出错";
    
    // 认证、授权相关的信息
    public static final String auAuthorInvalid = "400::AuthorizationInvalid::解析Authorization串失败";
    public static final String auAuthorIrregular = "400::AuthorizationIrregular::Authorization串不符合规范";
    public static final String auAccessKeyInvalid = "403::AccessKeyInvalid::accessKey不存在/无效";
    public static final String auAccessDenied = "403::AccessDenied::签名验证失败";
    public static final String auAppKeyNotMatchAppId = "403::AppKeyNotMatchAppId::传递的appKey与appId不匹配";
    
    // BSS相关的信息
    public static final String bssAccessOccurError = "500::BssAccessOccurError::访问BSS出错";
    public static final String bssReturnError = "500::BssReturnError::BSS返回错误信息";
    
    // 短信相关的信息
    public static final String smsContentTooLarge = "400::SmsContentTooLarge::短信内容过长";
    public static final String smsMobilesTooMany = "400::SmsMobilesTooMany::接收者数量过多";
    public static final String smsMobilesInvalid = "400::SmsMobilesInvalid::手机号不合格式";
    public static final String smsHasBlackKeys = "400::SmsHasBlackKeys::短信内容中存在黑关键字";
    
    
    // 扩展码相关的信息
    public static final String excodeChooseError = "400::ExcodeChooseError::选取扩展码失败，请重试";
    public static final String excodeNotEnough = "400::ExcodeNotEnough::无可用扩展码";
    public static final String excodeNeeds = "400::ExcodeNeeds::需要预选扩展码";
    public static final String excodeExists = "400::ExcodeExists::已经取选扩展码";
    
    // 用户相关
    public static final String userNotActive = "406::UserNotActive::用户处于不可用状态";
    public static final String isvPaidServiceOff = "406::IsvPaidServiceOff::ISV不支持代付费，扣费失败";
    public static final String isvPostPaidOff = "406::IsvPostPaidOff::ISV不支持后付费，扣费失败";
    
    
    // 公共服务相关
    public static final String serNotAuthorize = "406::ServiceNotAuthorize::未授权服务";
    public static final String serExpired = "406::ServiceExpired::服务已过期";
    public static final String serNotSupported = "400::NotSupported::不支持此服务";
    public static final String serNotSupportedType = "400::NotSupportedType::不支持此服务类型";
    public static final String serNotSupportedPaidOpt = "400::NotSupportedPaidOpt::不支持此付费类型";
    public static final String serNotSupportedPeriodType = "400::NotSupportedPeriodType::不支持此付费类型";
    public static final String serNotSupportedTimeUnit = "400::NotSupportedTimeUnit::不支持此周期单位类型";
    public static final String serInvalidUnitBaseNum = "400::InvalidUnitBaseNum::不合法的周期单位基数";
    public static final String serInvalidPeriodNum = "400::InvalidPeriodNum::不合法的周期数量";
    public static final String serNotSupportedDeductPriority = "400::NotSupportedDeductPriority::不支持此扣费级别";
    public static final String serInvalidSubscripIdPre = "400::InvalidSubscripIdPre::无效的订阅ID";
    
    // 第三方应用相关
    public static final String appServNotRegistered = "400::AppServNotRegistered::应用未注册使用服务";
    public static final String appServNOtActive = "400::AppServNOtActive::不可用状态";
    
    // 鉴权相关
    public static final String actDeductFail = "400::ActDeductFail::扣费失败";
    public static final String actRollBackFail = "400::ActRollBackFail::回滚费用失败";
    public static final String actNotExist = "400::ActNotExist::相应的用户账户不存在";
    public static final String actBalanceNotEnough = "400::ActBalanceNotEnough::账户余额不足";
    public static final String actSub2ActError = "400::ActSub2ActError::由订阅生成账户失败";
    public static final String actNOtSuppotReNew = "400::ActNOtSuppotReNew::账户当前状态不支持续订/取消";
    public static final String actNoSubscription = "400::NoSubscription::没有相应的订阅记录";
    public static final String actSubNOtSuppotReNew = "400::ActSubNOtSuppotReNew::当产订阅记录的状态不支持续订";
    public static final String actNoDeduct = "400::ActNoDeduct::没有相应的扣费记录";
    
    public static final String commonAccessError = "400::CommonAccessError::鉴权访问失败";
    
    
    // DB相关的信息
    public static final String dbUnreachable = "503::DBUnreachable::连接数据库错误"; 
    public static final String dbOpeOccurError = "503::DBOpeOccurError::操作数据库出现错误";
    public static final String dbNotFindEntity = "400::DBNotFindEntity::在数据库中没有查询到相应数据";
    
    // 系统相关的信息
    public static final String sysOccurError = "503::SysOccurError::系统发生未知错误"; 
    
    
    
    // 邮件相关的信息
    public static final String mailTitleTooLarge = "400::MailTitleTooLarge::邮件标题过长";
    public static final String mailContentTooLarge = "400::MailContentTooLarge::邮件内容过长";
    public static final String mailSignatureTooLarge = "400::MailSignatureTooLarge::邮件签名过长";
    public static final String mailReceiversNumInvalid = "400::MailReceiversNumInvalid::接收者数量无效：为零/过多";
    public static final String mailReceiversTooLong = "400::MailReceiversTooLong::接收者字符串过长";
    public static final String mailAddrInvalid = "400::MailAddrInvalid::邮箱格式不合法";
    public static final String mailHasBlackKeys = "400::MailHasBlackKeys::邮件中存在黑关键字";
    public static final String mailNonExist = "400::MailNonExist::无此邮件";
    public static final String mailStatusNotSupport = "400::MailStatusNotSupport::业务邮件不提供发送状态的查询";
    public static final String mailStatusNonExist = "400::MailStatusNonExist::目前尚无反馈的邮件发送状态，发送状态应在在邮件发送1日后查询。";
    
    
    
    public static final Map<String, Result> resultKV = new HashMap<String, Result>();
    
    static {
        addResult(uniSuccess);
        addResult(uniLostParams);
        addResult(uniParamsInvalid);
        addResult(uniTimeParseError);
        addResult(uniTimeError);
        addResult(uniUnvalidUrl);
        addResult(uniParseJsonError);
        addResult(uniParseBizDataError);
        
        addResult(auAuthorInvalid);
        addResult(auAuthorIrregular);
        addResult(auAccessKeyInvalid);
        addResult(auAccessDenied);
        addResult(auAppKeyNotMatchAppId);
        
        addResult(bssAccessOccurError);
        addResult(bssReturnError);
        
        addResult(smsContentTooLarge);
        addResult(smsMobilesTooMany);
        addResult(smsMobilesInvalid);
        addResult(smsHasBlackKeys);
        
        addResult(excodeChooseError);
        addResult(excodeNotEnough);
        addResult(excodeNeeds);
        addResult(excodeExists);
        
        addResult(userNotActive);
        addResult(isvPaidServiceOff);
        addResult(isvPostPaidOff);
        
        
        addResult(serNotAuthorize);
        addResult(serExpired);
        addResult(serNotSupported);
        addResult(serNotSupportedType);
        addResult(serNotSupportedPaidOpt);
        addResult(serNotSupportedPeriodType);
        addResult(serNotSupportedTimeUnit);
        addResult(serInvalidUnitBaseNum);
        addResult(serInvalidPeriodNum);
        addResult(serNotSupportedDeductPriority);
        addResult(serInvalidSubscripIdPre);
        
        addResult(appServNotRegistered);
        addResult(appServNOtActive);
        
        addResult(actDeductFail);
        addResult(actRollBackFail);
        addResult(actNotExist);
        addResult(actBalanceNotEnough);
        addResult(actSub2ActError);
        addResult(actNOtSuppotReNew);
        addResult(actNoSubscription);
        addResult(actSubNOtSuppotReNew);
        addResult(actNoDeduct);
        
        addResult(commonAccessError);
        
        addResult(dbUnreachable);
        addResult(dbOpeOccurError);
        addResult(dbNotFindEntity);
        
        
        addResult(sysOccurError);
        
        addResult(mailTitleTooLarge);
        addResult(mailContentTooLarge);
        addResult(mailSignatureTooLarge);
        addResult(mailReceiversNumInvalid);
        addResult(mailReceiversTooLong);
        addResult(mailAddrInvalid);
        addResult(mailHasBlackKeys);
        addResult(mailNonExist);
        addResult(mailStatusNotSupport);
        addResult(mailStatusNonExist);
        
    }
    
    // 向resultKV中添加返回值信息
    private static void addResult(String key) {
        String[] strs = key.split(Constants.DOUB_COLON);
        resultKV.put(key, new Result(NumberUtils.toInt(strs[0]), strs[1], strs[2]));
    }
    
    // 获取Result
    public static Result getResult(String key) {
        return resultKV.get(key);
    }
    
    // 替换返回信息，如查询到的上行短信信息
    public static Result modifyInfo(String code, String info) {
        Result result = resultKV.get(code).copy();
        result.setInfo(info);
        return result;
    }
    
    // 拼接错误细节，如错误的手机号
    public static Result appendInfo(String code, String info) {
        Result result = resultKV.get(code).copy();
        result.setInfo(result.getInfo() + Constants.DOUB_COLON + info);
        return result;
    }
    
}
