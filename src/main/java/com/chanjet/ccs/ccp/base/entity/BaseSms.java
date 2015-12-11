package com.chanjet.ccs.ccp.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.chanjet.ccs.base.entity.BaseEntity;

/**
 * 短信
 * 
 * @Date 2013-7-24 上午11:20:30
 */

@MappedSuperclass
public class BaseSms extends BaseEntity {

    private String bssAccessToken;      // 用户token，来自于Bss
    private String bssOrgAccessToken;   // 企业token，来自于Bss
    private String bssUserId;           // 用户的唯一标识，来自于Bss
    private String bssOrgId;            // 企业的唯一标识，来自于Bss
    private String bssAppId;            // 应用的唯一标识，来自于Bss
    private String bssAppKey;           // 应用的访问Key，来自于Bss
    private String bssAppSecret;        // 应用的访问密钥，来自于Bss
    private String content;             // 未经拆分的短信内容
    private String mobiles;             // 手机号
    private int count;                  // 发送人数
    private int splitCount;             // 单条短信被拆分的数量
    private int billingCount;           // 拆分后短信总数量
    private int failureCount = 0;       // 发送失败的人数
    private Date createTime;            // 短信创建时间
    private Date timingTime;            // 定时发送时间
    private Date sendedTime;            // 实际发送时间
    private int smsType;                // 类别（系统短信、用户短信、通知类短信、营销类短信）
    private String extendCode;          // 扩展码
    private int level = 1;              // 短信级别（1:第一级, 2:第二级, 3:第三级, 4:第四级）
    private String business;            // 业务类型（.....）
    private String usedChannel;         // 考虑常量的表示 实际发送使用的通道
    private int state = -2;             // 群发状态（-2:尚未发送, -1:正在发送, 0:发送失败, 1:成功发送）
    private int billingState = -1;      // 对账状态（-1:未对账, 1:已对账）
    private int syncFlag = -1;          // 同步标识（-1:未同步, 1:已同步）
    private String errMsg;              // 失败明细
    private String billingNo;           // 账单No
    private String report;              // 状态报告
    private Date reportTime;            // 状态报告的完成时间
    private String identifier;          // 唯一标识

    public BaseSms() { }

    
    @Column(name = "bss_access_token", length = 36, nullable = true)
    public String getBssAccessToken() {
        return bssAccessToken;
    }

    public void setBssAccessToken(String bssAccessToken) {
        this.bssAccessToken = bssAccessToken;
    }

    @Column(name = "bss_org_access_token", length = 36, nullable = true)
    public String getBssOrgAccessToken() {
        return bssOrgAccessToken;
    }

    public void setBssOrgAccessToken(String bssOrgAccessToken) {
        this.bssOrgAccessToken = bssOrgAccessToken;
    }

    @Column(name = "bss_user_id", length = 36, nullable = true)
    public String getBssUserId() {
        return bssUserId;
    }

    public void setBssUserId(String bssUserId) {
        this.bssUserId = bssUserId;
    }

    @Column(name = "bss_org_id", length = 36, nullable = true)
    public String getBssOrgId() {
        return bssOrgId;
    }

    public void setBssOrgId(String bssOrgId) {
        this.bssOrgId = bssOrgId;
    }

    @Column(name = "bss_app_id", length = 36, nullable = false)
    public String getBssAppId() {
        return bssAppId;
    }

    public void setBssAppId(String bssAppId) {
        this.bssAppId = bssAppId;
    }
    
    @Column(name = "bss_app_key", length = 36, nullable = false)
    public String getBssAppKey() {
        return bssAppKey;
    }

    public void setBssAppKey(String bssAppKey) {
        this.bssAppKey = bssAppKey;
    }

    @Column(name = "bss_app_secret", length = 36, nullable = false)
    public String getBssAppSecret() {
        return bssAppSecret;
    }

    public void setBssAppSecret(String bssAppSecret) {
        this.bssAppSecret = bssAppSecret;
    }

    @Column(name = "content", length = 600, nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "mobiles", length = 13000, nullable = false)
    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    @Column(name = "count", length = 32, nullable = false)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Column(name = "split_count", length = 32, nullable = false)
    public int getSplitCount() {
        return splitCount;
    }

    public void setSplitCount(int splitCount) {
        this.splitCount = splitCount;
    }

    @Column(name = "billing_count", length = 32, nullable = false)
    public int getBillingCount() {
        return billingCount;
    }

    public void setBillingCount(int billingCount) {
        this.billingCount = billingCount;
    }

    @Column(name = "failure_count", length = 32, nullable = true)
    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "timing_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getTimingTime() {
        return timingTime;
    }

    public void setTimingTime(Date timingTime) {
        this.timingTime = timingTime;
    }

    @Column(name = "sended_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getSendedTime() {
        return sendedTime;
    }

    public void setSendedTime(Date sendedTime) {
        this.sendedTime = sendedTime;
    }

    @Column(name = "sms_type", length = 2, nullable = true)
    public int getSmsType() {
        return smsType;
    }

    public void setSmsType(int smsType) {
        this.smsType = smsType;
    }

    @Column(name = "extend_code", length = 16, nullable = true)
    public String getExtendCode() {
        return extendCode;
    }

    public void setExtendCode(String extendCode) {
        this.extendCode = extendCode;
    }

    @Column(name = "level", length = 2, nullable = false)
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Column(name = "business", length = 32, nullable = true)
    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    @Column(name = "used_channel", length = 32, nullable = true)
    public String getUsedChannel() {
        return usedChannel;
    }

    public void setUsedChannel(String usedChannel) {
        this.usedChannel = usedChannel;
    }

    @Column(name = "state", length = 2, nullable = false)
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Column(name = "billing_state", length = 2, nullable = false)
    public int getBillingState() {
        return billingState;
    }

    public void setBillingState(int billingState) {
        this.billingState = billingState;
    }

    @Column(name = "sync_flag", length = 2, nullable = false)
    public int getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(int syncFlag) {
        this.syncFlag = syncFlag;
    }

    @Column(name = "err_msg", length = 255, nullable = true)
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Column(name = "billing_no", length = 36, nullable = true)
    public String getBillingNo() {
        return billingNo;
    }

    public void setBillingNo(String billingNo) {
        this.billingNo = billingNo;
    }

    @Column(name = "report", length = 32, nullable = true)
    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    @Column(name = "report_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    @Column(name = "identifier", length = 36, nullable = true)
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
