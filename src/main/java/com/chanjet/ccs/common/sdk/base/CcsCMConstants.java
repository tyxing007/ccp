package com.chanjet.ccs.common.sdk.base;

import com.chanjet.ccs.common.sdk.util.ConfigUtil;

public class CcsCMConstants {
    
    // Header中的Authorization
    public static final String kAuthorization = "Authorization";
    
    public static final String kServiceKey = "serviceKey";
    public static final String kParamInfo = "paramInfo";
    public static final String kAuthInfo = "authInfo";
    
    public static final String kJsonStr = "jsonStr";
    public static final String kDate = "date";
    
    public static final String kCcsId = "ccsId";
    public static final String kOrgId = "orgId";
    public static final String kDeductNo = "deductNo";
    public static final String kRobackValue = "robackValue";
    public static final String kSubServiceId = "subServiceId";
    
    //public static final String urlHost = "http://localhost:8180";    // 域名
    public static final String urlHost = ConfigUtil.getConfigContent("common", "common_url");              // 域名
    public static final String urlAmount = urlHost + "/common/getServiceAmount";      // 获取服务余额
    public static final String urlSubscribe = urlHost + "/common/subscription";       // 订阅服务
    public static final String urlUnsubscribe = urlHost + "/common/unSubscription";   // 取消订阅
    public static final String urlDeduct = urlHost + "/common/deduct";                // 扣费
    public static final String urlRollback = urlHost + "/common/robackDeduct";        // 回滚
    
    
    public static final String UTF8 = "UTF-8";
    public static final String GB2312 = "GB2312";
    
    
    public static final String QUES_MARK = "?";
    public static final String EQUAL_SIGN = "=";
    public static final String AND = "&";
    
    
    
}
