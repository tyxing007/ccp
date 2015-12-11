package com.chanjet.ccs.base.basic;

import com.chanjet.ccs.ccp.base.util.Configure;

public class ConstantsCcs {
    
    // 服务URL
    public static final String urlApp = "/app";
    public static final String urlService = "/service";
    
    // 调用者传入的用于鉴权时的key值
    public static final String kAppKey = "appKey";          // BSS分配
    public static final String kParamInfo = "paramInfo";    // 
    public static final String kAuthInfo = "authInfo";      // 加密后的paramInfo
    
    
    // 调用者传入的用于鉴权的业务参数Key（paramInfo中的属性名称）
    public static final String kToken = "token";                // BSS分配的用户Token
    public static final String kOrgToken = "orgToken";          // BSS分配的企业Token
    public static final String kReferer = "referer";            // 业务说明
    public static final String kDate = "date";                  // 请求时间
    public static final String kAppId = "appId";                // Bss分配的应用ID
    public static final String kOrgId = "orgId";                // Bss分配的企业ID
    public static final String kUserId = "userId";              // Bss分配的用户ID
    
    
    // auth相关属性
    public static final String kBssAccessToken = "bssAccessToken";
    public static final String kBssOrgAccessToken = "bssOrgAccessToken";
    public static final String kBssUserId = "bssUserId";
    public static final String kBssOrgId = "bssOrgId";
    public static final String kBssAppId = "bssAppId";
    public static final String kBssAppKey = "bssAppKey";        // BSS分配
    public static final String kBssAppSecret = "bssAppSecret";
    public static final String kBssIsvId = "bssIsvId";
    
    
    public static final String kService = "service";
    
    
    public static final String vAppKey = Configure.getString("bss.appKey");                 // BSS分配的公钥
    public static final String vAppSecret = Configure.getString("bss.appSecret");           // BSS分配的私钥
    
    
    public static final int vReqExpire = 
            Configure.parseInt("request.expire", 15, 1, 6000) * 60 * 1000;                  // 请求过期时间值
    
    public static final String kReqIpAndTime = "reqIpAndTime";
    
    public static final String SERV_SMS = "SMS";                // 普通短信
    public static final String SERV_UPSMS = "UPSMS";            // 可上行短信
    
    public static final String cachePre = "CCS_";                   // 缓存前缀
    public static final String cacheUserPre = cachePre + "USER_";   // 缓存前缀
    public static final String cacheAppPre = cachePre + "APP_";     // 缓存前缀
    //public static final String cacheTokenPre = cachePre + "TOKEN_"; // 缓存前缀
    
    public static final String cacheCcpPre = "CCS_CCP_";                     // 缓存前缀
    public static final String cacheCcpAmtPre = cacheCcpPre + "AMT_";      // 缓存前缀
    
    public static final String cacheSesPre = "CCS_SES_";                     // 缓存前缀
    public static final String cacheSesAmtPre = cacheSesPre + "AMT_";      // 缓存前缀
    
    public static final int defaultLevel = Integer.parseInt(Configure.getString("defaultLevel"));
    public static final int srLevel = Integer.parseInt(Configure.getString("sendAndReceiveLevel"));
    
    //zookeeper config
    public static final String resourceFile = "datasource";
    public static final String connectionString = "connectStr";
    public static final String rootNode = "rootNode";
    public static final String version = "version";
}
