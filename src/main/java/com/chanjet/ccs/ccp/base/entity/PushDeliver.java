package com.chanjet.ccs.ccp.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.chanjet.ccs.base.entity.BaseEntity;

// TODO MEMO 修改了userName，原先是username，及表中的user_name

@Entity
@Table(name = "t_push_deliver")
public class PushDeliver extends BaseEntity {
    
    private String isvName;         // ISV的名称
    private String url;             // url
    private String parameter;       // 能提供的参数
    private String extendCode;      // 扩展码
    private Integer isReceipt;      // 是否回执
    private String receiptRule;     // 回执规则
    private String userName;        // 账号
    private String password;        // 密码
    private String prefix;          // 回执前缀
    
	public PushDeliver() { }

	public PushDeliver(Integer id, String isvName, String url,
			String parameter, String extendCode, Integer isReceipt,
			String receiptRule, String userName, String password, String prefix) {
		this.id = id;
		this.isvName = isvName;
		this.url = url;
		this.parameter = parameter;
		this.extendCode = extendCode;
		this.isReceipt = isReceipt;
		this.receiptRule = receiptRule;
		this.userName = userName;
		this.password = password;
		this.prefix = prefix;
	}

	@Column(name = "isv_name")
	public String getIsvName() {
		return isvName;
	}

	public void setIsvName(String isvName) {
		this.isvName = isvName;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "parameter")
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Column(name = "extend_code")
	public String getExtendCode() {
		return extendCode;
	}

	public void setExtendCode(String extendCode) {
		this.extendCode = extendCode;
	}

	@Column(name = "is_receipt")
	public Integer getIsReceipt() {
		return isReceipt;
	}


	public void setIsReceipt(Integer isReceipt) {
		this.isReceipt = isReceipt;
	}

	@Column(name = "receipt_rule")
	public String getReceiptRule() {
		return receiptRule;
	}


	public void setReceiptRule(String receiptRule) {
		this.receiptRule = receiptRule;
	}
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name = "prefix")
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
