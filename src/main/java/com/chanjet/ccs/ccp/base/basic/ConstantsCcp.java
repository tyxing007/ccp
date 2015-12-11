package com.chanjet.ccs.ccp.base.basic;

import java.util.ArrayList;
import java.util.List;

import com.chanjet.ccs.base.basic.Constants;
import com.chanjet.ccs.base.util.ReflectUtil;
import com.chanjet.ccs.ccp.base.entity.Sms;
import com.chanjet.ccs.ccp.base.util.Configure;


public class ConstantsCcp {
    
    /** 系统名称 */
    //public static final String sysName = "ccp";
    /** 系统配置文件名称 */
    //public static final String confSys = "system.properties"; // TODO remove
    
    
//    private static final String _appKey = Configure.getString("bss.appKey");
//    private static final String _appSecret = Configure.getString("bss.appSecret");
//    
//    private static final String _dfAppKey = "a0e9b9a6-f6e8-432c-a4cc-bca5e5a4b104";
//    private static final String _dfAppSecret = "dseees";
//    
//    public static final String vAppKey = _appKey == null ? _dfAppKey : _appKey;                 // BSS分配的公钥
//    public static final String vAppSecret = _appSecret == null ? _dfAppSecret : _appSecret;     // BSS分配的私钥
//    
//    // 使用BSS的测试环境还是生产环境？
//    public static final boolean isBssTest = Configure.parseBool("bss.test");
    

    public static final int vContentLength = 
            Configure.parseInt("sms.content.length", 500, 1, 500);              // 调用接口时，每条短信的长度限制
    public static final int vContentLengthPerSms = 
            Configure.parseInt("sms.content.length.per.sms", 66, 1, 66);        // 每条短信长度
    public static final int vMobilesNum = 
            Configure.parseInt("sms.mobils.num", 1000, 1, 1000);                // 调用接口时，接收者数量限制
    
    public static final int vTimingTimeBuffer = 
            Configure.parseInt("sms.send.time.buffer", 5, 0, 10);               // 定时发送时间允许的时间差
    
    public static final int vCacheTimeout = 
            Configure.parseInt("cache.timeout", 600, 0, 600);                   // 缓存时间设置
    
    
    private static final String _cacheApiPre = Configure.getString("cache.ccpapi.prefix");
    private static final String _cacheEnginePre = Configure.getString("cache.ccpengine.prefix");        
    private static final String _cacheTaskPre = Configure.getString("cache.ccptask.prefix");
    private static final String _cacheChannelIdPre = Configure.getString("cache.channelID.prefix");
    
    public static final String vCacheApiPre = _cacheApiPre == null ? "ccpApi_" :_cacheApiPre;                       // 缓存中ccpApi前缀
    public static final String vCacheEnginePre = _cacheEnginePre == null ? "ccpEngine_" :_cacheEnginePre;           // 缓存中ccpEngine前缀
    public static final String vCacheTaskPre = _cacheTaskPre == null ? "ccpTask_" :_cacheTaskPre;                   // 缓存中ccpTask前缀
    public static final String vCacheChannelIdPre = _cacheChannelIdPre == null ? "channelID_" :_cacheChannelIdPre;  // 缓存中通道计数缓存变量的前缀
    
    
    private static final String _signPrefix = Configure.getString("sign.prefix");
    private static final String _signSuffix = Configure.getString("sign.suffix");
    private static final String _signUPContent = Configure.getString("sign.up.content");
    private static final String _signContent = Configure.getString("sign.content");
    
    public static final String vSignPrefix = _signPrefix == null ? "【" : _signPrefix;                   // 上行短信签名起始符
    public static final String vSignSuffix = _signSuffix == null ? "】" : _signSuffix;                   // 上行短信签名结束符
    public static final String vSignUPContent = _signUPContent == null ? "【畅捷通】" : _signUPContent;    // 上行短信的默认签名
    public static final String vSignContent = _signContent == null ? "【快讯】" : _signContent;            // 普通短信的默认签名
        
    
    public static final int vExcodeLimit = 
            Configure.parseInt("excode.list.num", 1000, 0, 1000);               // 查询可用扩展号时数量限制
    public static final int vExcodeChoiceNum = 
            Configure.parseInt("excode.choice.num", 10, 0, 20);                 // 用户选择扩展号时的数量限制
    
    //public static final String CACHE_ATTR_USER = "user";        //代表Cache缓存中的user变量
    //public static final String CACHE_ATTR_ACCOUNT = "account";  //代表Cache缓存中的account变量
    
    
    public static final String smsSystem = "system";                // 系统类短信
    public static final String smsUser = "user";                    // 用户类短信
    public static final String smsNotice = "notice";                // 通知类短信
    public static final String smsMarket = "market";                // 营销类短信
    public static final String smsAudio = "audio";                // 语音类短信
    
    
    // 服务URL
    public static final String urlSms = "/sms";                             // 短信
    public static final String urlSmsSystem = Constants.SLASH + smsSystem;  // 系统类短信
    public static final String urlSmsUser =  Constants.SLASH + smsUser;     // 用户类短信
    public static final String urlSmsNotice =  Constants.SLASH + smsNotice; // 通知类短信
    public static final String urlSmsMarket =  Constants.SLASH + smsMarket; // 营销类短信
    public static final String urlSmsAudio =  Constants.SLASH + smsAudio; // 语音类短信
    public static final String urlSmsCheck = "/{smsType}/check";            // 检查黑关键字
    public static final String urlAmount = "/{smsType}/amount";             // 获取余额
    public static final String urlSmsStatus = "/{smsId}";                   // 短信ID
    public static final String urlDeliver = "/deliver";                     // 上行数据
    public static final String urlChannel = "/channel";                     // 通道设置
    public static final String urlExcode = "/excode";                       // 扩展号
    public static final String urlExcodeRandom = "/random";                 // 随机选择
    public static final String urlExcodeChoice = "/choice";                 // 获取选项
    public static final String urlExcodeChoose = "/choose";                 // 选择扩展号
    
    
    
    public static final String kContent = "content";
    public static final String kMobiles = "mobiles";
    public static final String kTimingTime = "timingTime";
    
    
    public static final String kCount = "count";
    public static final String kSplitCount = "splitCount";
    public static final String kBillingCount = "billingCount";
    public static final String kSmsType = "smsType";
    //public static final String kFailureCount = "failureCount";
    //public static final String kCreateTime = "createTime";
    //public static final String kSendedTime = "sendedTime";
    //public static final String kExtendCode = "extendCode";
    //public static final String kLevel = "level";
    public static final String kBusiness = "business";
    //public static final String kBillingType = "billingType";
    //public static final String kUsedChannel = "usedChannel";
    public static final String kState = "state";
    //public static final String kBillingState = "billingState";
    //public static final String kSyncFlag = "syncFlag";
    //public static final String kErrMsg = "errMsg";
    //public static final String kBillingNo = "billingCount";
    //public static final String kReport = "report";
    //public static final String kReportTime = "reportTime";
    //public static final String kIdentifier = "identifier";
    public static final String kCodeName = "codeName";
    
    
    
    public static final List<String> attrsSms = new ArrayList<String>();       // 短信实体类属性集合
    static {
        for (String attr : ReflectUtil.getAttrNames(Sms.class))
            attrsSms.add(attr);
    }
    
    // ccpEngine中任务名称
    public static final String taskSms = "sms";                 // 短信任务（发短信）
    public static final String taskUp = "up";                   // 获取上行数据任务
    public static final String taskBalance = "balance";         // TODO
    public static final String taskStateReport = "stateReport"; // TODO 
    
    
    // CcpTask任务名称
    public static final String EXTENDCODE_CHECK = "task_extendcode_check";  // 扩展码检查OK
    public static final String SMS_SEND_REPORT = "task_sms_send_report";    // 每天发送情況OK
    public static final String JVM_SEND = "task_jvm_send";                  // JVM定时发送
    public static final String SMS_CHANGE_CHECK = "task_sms_check";         // 通道检查OK
    public static final String SMS_DEDUCT = "task_sms_deduct";              // 短信对账OK
    public static final String TIMING_SMS_SEND = "task_time_sms_send";      // 定时短信OK
    public static final String REPORT = "task_report";                      // 回执报告OK
    public static final String NEWSMS_UP = "task_newsms_up";                // 短信上行接收的定时任务OK
    public static final String SYN_HIS = "task_sys_his";                    // 同步历史数据OK
    public static final String SYN_DATE_CCPWEB = "task_syn_date_ccpweb";    // 同步历史数据OK
    
    public static final String cachePre = "CCS_CCP_";                   // 缓存前缀
    public static final String cacheAmountPre = cachePre + "AMOUNT_";   // 缓存前缀
    //public static final String cacheAppPre = cachePre + "APP_";     // 缓存前缀
    //public static final String cacheTokenPre = cachePre + "TOKEN_"; // 缓存前缀
    
    // zookeeper configure item urls
    public static final String DBClassName = "classname";
    public static final String DBURL = "url";
    public static final String DBUSERNAME = "username";
    public static final String DBPASSWORD = "password";
    public static final String MQURL = "url";
    public static final String MQUSERNAME = "username";
    public static final String MQPASSWORD = "password";
    public static final String MQTOPIC = "messagetopic";
    public static final String RedisMASTER = "master";
    public static final String RedisSentinels_NODE1 = "sentinels.node1";
    public static final String RedisSentinels_PORT1 = "sentinels.port1";
    public static final String RedisSentinels_NODE2 = "sentinels.node2";
    public static final String RedisSentinels_PORT2 = "sentinels.port2";
    public static final String RedisMAXTOTAL = "maxTotal";
    public static final String RedisMAXIDLE = "maxIdle";
    public static final String RedisMAXWAIT = "maxWaitMillis";
    public static final String RedisTESTONBORROW = "testOnBorrow";
    public static final String RedisTESTONRETURN = "testOnReturn";
    
    //config file
    public static final String DATASOURCE = "datasource";
    public static final String ZKURL = "zkurl";
    public static final String VERSION = "version";
    public static final String ROOTURL = "rooturl";
    public static final String DB = "db";
    public static final String MQ = "mq";
    public static final String REDIS = "redis";
}
