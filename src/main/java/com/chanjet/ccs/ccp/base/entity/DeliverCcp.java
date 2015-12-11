package com.chanjet.ccs.ccp.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.chanjet.ccs.base.entity.BaseEntity;

@Entity
@Table(name="t_deliver_ccp")
public class DeliverCcp extends BaseEntity {

    private String isvName;         // IVS的名称
    private String url;             // url
    private String parameter;       // 能提供的参数
    private String exCode;          // 扩展码
    private int isReceipt;          // 是否回执
    private String receiptRule;     // 回执规则
    private String userName;        // 账号
    private String password;        // 密码
    private String prefix;          // 回执前缀

    
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

    @Column(name = "excode")
    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

    @Column(name = "is_receipt")
    public int getIsReceipt() {
        return isReceipt;
    }

    public void setIsReceipt(int isReceipt) {
        this.isReceipt = isReceipt;
    }

    @Column(name = "receipt_rule")
    public String getReceiptRule() {
        return receiptRule;
    }

    public void setReceiptRule(String receiptRule) {
        this.receiptRule = receiptRule;
    }

    @Column(name = "username")
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
