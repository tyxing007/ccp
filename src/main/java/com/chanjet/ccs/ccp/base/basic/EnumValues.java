package com.chanjet.ccs.ccp.base.basic;


public class EnumValues {
    
    // 用户类型
    public static final int USER_TYPE_ISV = 1;          // ISV用户
    public static final int USER_TYPE_COMPANY = 2;      // 企业用户

    // 短信类型
    public static final int SMS_TYPE_SYSTEM = 1;        // 系统类短信（可上行）
    public static final int SMS_TYPE_USER = 2;          // 用户类短信（可上行）
    public static final int SMS_TYPE_NOTICE = 3;        // 通知类短信
    public static final int SMS_TYPE_MARKET = 4;        // 营销类短信

    // 短信、用户、黑关键字、通道级别
    public static final int LEVEL_FIRST = 1;            // 第一级
    public static final int LEVEL_SECOND = 2;           // 第二级
    public static final int LEVEL_THIRD = 3;            // 第三级
    public static final int LEVEL_FOUTH = 4;            // 第四级
    public static final int LEVEL_FIFTH = 5;            // 第五级
    public static final int LEVEL_ZERO = 0;             // 第零级 不发送短信级别

    // 短信发送状态
    public static final int SMS_UNSEND = -2;            // 尚未发送
    public static final int SMS_SENDING = -1;           // 正在发送
    public static final int SMS_FAILED = 0;             // 发送失败
    public static final int SMS_SENDED = 1;             // 成功发送

    // 对账状态
    public static final int BILLING_STATE_UNCHECK = -1; // 未对账
    public static final int BILLING_STATE_CHECKED = 1;  // 已对账

    // 同步标识
    public static final int SYNC_FLAG_UNSYNCED = -1;    // 未同步
    public static final int SYNC_FLAG_SYNCED = 1;       // 已同步
    
    // 用户、通道、应用状态
    public static final int STATUS_DISABLE = -1;         // 不可用/非活跃
    public static final int STATUS_ENABLE = 1;           // 可用/活跃
    
    // 扩展号状态
    public static final int EXCODE_DISABLE = -2;         // 不可用
    public static final int EXCODE_UNACTIVE = -1;        // 尚未使用
    public static final int EXCODE_ACTIVE = 1;           // 已被使用
    
    // 上行的短信是否自动回执
    public static final int SMS_RECEIPT = 1;            // 开启回执
    public static final int SMS_NO_RECEIPT = 0;         // 关闭回执
    
//    public static final Integer  RECEIPT=1;//开启回执
//    public static final Integer NO_RECEIPT=0;//关闭回执
//    public static final String SUCCESS_RESULT="0";//调用ccp接口完全成功，将返回0
//    public static final String SUCCESS="200";//http 正常接收状态
    
    // 扩展号分配模式
    public static final int EXCODE_STATIC = 1;          // 固定扩展号
    public static final int EXCODE_DYNAMIC = 2;         // 动态分配扩展号
    
    // 扩展号拥有者
    //public static final int EXCODE_ISV = 1;             // ISV的
    public static final int EXCODE_APP = 1;             // 应用
    public static final int EXCODE_APP_ORG = 2;         // 某应用中某企业
    public static final int EXCODE_APP_USER = 3;        // 某应用中某用户
    
    // ???
    public static final String SUCCESS_RESULT = "0";    // 调用ccp接口完全成功，将返回0
    public static final String SUCCESS = "200";         // http正常接收状态
    
    // 邮件显示格式
    public static final int MAIL_FORM_HTML = 1;         // Html格式邮件
    public static final int MAIL_FORM_TXT = 2;          // 文本格式邮件
    
    // 邮件类型
    public static final int MAIL_TYPE_BUSI = 1;         // 业务邮件
    public static final int MAIL_TYPE_MARK = 2;         // 营销邮件
    
    // 邮件发送状态
    public static final int MAIL_UNSEND = -2;           // 尚未发送
    public static final int MAIL_SENDING = -1;          // 正在发送
    public static final int MAIL_FAILED = 0;            // 发送失败
    public static final int MAIL_PART_FAILED = 1;       // 部分成功
    public static final int MAIL_SENDED = 2;            // 发送成功
    
    // 邮件同步锁标志，用于历史数据迁移
    public static final int MAIL_UNSYNC = -1;           // 未同步
    public static final int MAIL_SYNC = 1;              // 同步过程中
    
    // 是否忽略错误邮件
    public static final int MAIL_IGNORE_ON = 1;         // 忽略错误邮件
    public static final int MAIL_IGNORE_OFF = 2;        // 不忽略错误邮件
    
    // 是否忽略错误邮件
    public static final int MARKETING_UNSYNC = 1;       // 营销邮件状态数据未同步
    public static final int MARKETING_SYNC= 2;          // 营销邮件状态数据已同步
    
    
    public static final int BOOL_TRUE = 1;             // 代表true
    public static final int BOOL_FALSE = -1;            // 代表false
    
    
    
    
    
}
