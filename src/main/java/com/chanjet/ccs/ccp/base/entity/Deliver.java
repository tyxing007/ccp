package com.chanjet.ccs.ccp.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.chanjet.ccs.base.entity.BaseEntity;


//TODO　MEMO 修改了failReason，原先:deliverReason，及表中的fail_reason。构造方法调整了参数顺序
/**
 * 上行短信
 * 2013-7-24 上午11:19:23
 */

@Entity
@Table(name="t_deliver")
public class Deliver extends BaseEntity {
    
    private String msgId;               // 在通道方中，短信的唯一标识
    private String mobile;              // 发送短信的手机号码
    private String extendCode;          // 扩展号码
    private String content;             // 短信消息内容
    private int msgLength;              // 消息长度
    private Date createTime;            // 创建时间
    private String serviceCode;         // 服务代码（即通道分配的号码）
    private String destinationId;       // 通道返回的目的号码（服务代码+分配号+扩展号码；该号码是手机用户短消息的被叫号码，也叫长号码）
    private int syncFlag = -1;          // 数据推送标识(-1:未推送，1:推送成功)
    private int deliverTimes;           // 推送次数
    private String failReason;          // 推送失败原因
    private int hasAccessed = -1;       // 用户是否通过接口获取回复短信(-1:未获取，1:已经获取)
    
    
    public Deliver() { }
    
    @Column(name = "msg_id")
    public String getMsgId() {
        return msgId;
    }
    
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    
    @Column(name = "mobile", nullable = false)
    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    @Column(name = "extend_code")
    public String getExtendCode() {
        return extendCode;
    }
    
    public void setExtendCode(String extendCode) {
        this.extendCode = extendCode;
    }
    
    @Column(name = "content")
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Column(name = "msg_length")
    public int getMsgLength() {
        return msgLength;
    }
    
    public void setMsgLength(int msgLength) {
        this.msgLength = msgLength;
    }
    
    @Column(name = "create_time", nullable = false)
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    @Column(name = "service_code")
    public String getServiceCode() {
        return serviceCode;
    }
    
    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
    
    @Column(name = "destination_id")
    public String getDestinationId() {
        return destinationId;
    }
    
    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }
    
    @Column(name = "sync_flag", nullable = false)
    public int getSyncFlag() {
        return syncFlag;
    }
    
    public void setSyncFlag(int syncFlag) {
        this.syncFlag = syncFlag;
    }
    
    @Column(name = "deliver_times", nullable = false)
    public int getDeliverTimes() {
        return deliverTimes;
    }
    
    public void setDeliverTimes(int deliverTimes) {
        this.deliverTimes = deliverTimes;
    }
    
    @Column(name = "fail_reason")
    public String getFailReason() {
        return failReason;
    }
    
    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    @Column(name = "has_accessed")
    public int getHasAccessed() {
        return hasAccessed;
    }
    public void setHasAccessed(int hasAccessed) {
        this.hasAccessed = hasAccessed;
    }
    
}
