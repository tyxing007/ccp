/**
 * 2015年12月11日下午2:17:02
 * wugqa@chanjet.com
 */
package com.chanjet.ccs.ccp.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.chanjet.ccs.base.entity.BaseEntity;

/**
 * 2015年12月11日下午2:17:02
 * wugqa@chanjet.com
 */
@Entity
@Table(name = "t_voice")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class VoiceSms extends BaseEntity{
	/**
	 * 被叫手机号，多个用逗号隔开
	 */
	private String mobiles;
	/**
	 * 验证码
	 */
	private String content;
	/**
	 * 定时发送时间
	 */
	private Date timingTime;
	/**
	 * 应用的访问Key，来自于Bss
	 */
	private String bssAppKey; 
	/**
	 * 应用的访问密钥，来自于Bss
	 */
	private String bssAppSecret;  
	/**
	 * 应用的唯一标识，来自于Bss
	 */
	private String bssAppId;  
	/**
	 * 入库时间
	 */
	private Date createTime;
	/**
	 * 发送状态
	 */
	private int state;
	/**
	 * 最后一次发送时间
	 */
	private Date sendedTime;
	/**
	 * 失败原因
	 */
	private String errorInfo;
	
	/**
	 * @return the mobiles
	 */
	@Column(name = "mobiles", length = 13000, nullable = false)
	public String getMobiles() {
		return mobiles;
	}
	/**
	 * @param mobiles the mobiles to set
	 */
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	/**
	 * @return the captcha
	 */
	@Column(name = "content", length = 255, nullable = false)
	public String getContent() {
		return content;
	}
	/**
	 * @param captcha the captcha to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the timingTime
	 */
	@Column(name = "timing_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
	public Date getTimingTime() {
		return timingTime;
	}
	/**
	 * @param timingTime the timingTime to set
	 */
	public void setTimingTime(Date timingTime) {
		this.timingTime = timingTime;
	}
	/**
	 * @return the bssAppKey
	 */
	@Column(name = "bss_app_key", length = 36, nullable = false)
	public String getBssAppKey() {
		return bssAppKey;
	}
	/**
	 * @param bssAppKey the bssAppKey to set
	 */
	public void setBssAppKey(String bssAppKey) {
		this.bssAppKey = bssAppKey;
	}
	/**
	 * @return the bssAppSecret
	 */
	@Column(name = "bss_app_secret", length = 36, nullable = false)
	public String getBssAppSecret() {
		return bssAppSecret;
	}
	/**
	 * @param bssAppSecret the bssAppSecret to set
	 */
	public void setBssAppSecret(String bssAppSecret) {
		this.bssAppSecret = bssAppSecret;
	}
	/**
	 * @return the bssAppId
	 */
	 @Column(name = "bss_app_id", length = 36, nullable = false)
	public String getBssAppId() {
		return bssAppId;
	}
	/**
	 * @param bssAppId the bssAppId to set
	 */
	public void setBssAppId(String bssAppId) {
		this.bssAppId = bssAppId;
	}
	/**
	 * @return the createDate
	 */
	@Column(name = "create_time", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the sendStatus
	 */
	@Column(name = "send_state", length = 2, nullable = false)
	public int getState() {
		return state;
	}
	/**
	 * @param sendStatus the sendStatus to set
	 */
	public void setState(int state) {
		this.state = state;
	}
	/**
	 * @return the sendDate
	 */
	@Column(name = "sended_time", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSendedTime() {
		return sendedTime;
	}
	/**
	 * @param sendDate the sendDate to set
	 */
	public void setSendedTime(Date sendedTime) {
		this.sendedTime = sendedTime;
	}
	/**
	 * @return the errorInfo
	 */
	@Column(name = "errorInfo", length = 13000, nullable = true)
	public String getErrorInfo() {
		return errorInfo;
	}
	/**
	 * @param errorInfo the errorInfo to set
	 */
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
}
