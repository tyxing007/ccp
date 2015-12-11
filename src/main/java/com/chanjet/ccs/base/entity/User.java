package com.chanjet.ccs.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.chanjet.ccs.base.basic.QueryNames;
import com.chanjet.ccs.ccp.base.basic.EnumValues;


// TODO MEMO enterpriseId在ccpTask的实体类中没有，检验之
/**
 * 用户（企业用户也是user）
 * @Date 2013-7-24 上午11:22:33
 */

@Entity
@Table(name="t_user")
@NamedQueries({ 
    @NamedQuery(name = QueryNames.userListByIdsAndServ, 
                query = " SELECT u FROM User u WHERE u.bssAppKey = ? AND u.bssOrgId = ? " +
                        " AND u.bssUserId = ? AND u.service = ? AND u.state = " + EnumValues.STATUS_ENABLE),
    @NamedQuery(name = QueryNames.isvListByIdsAndServ, 
            query = " SELECT u FROM User u WHERE u.bssAppKey = ? AND u.bssUserId = ? " +
                    " AND u.service = ? AND u.state = " + EnumValues.STATUS_ENABLE)
})
public class User extends BaseEntity{
    
    private String bssAppId;            // 应用的唯一标识，来自于Bss
    private String bssOrgId;            // 企业的唯一标识，来自于Bss
    private String bssUserId;           // 用户的唯一标识，来自于Bss
    private String bssAppKey;           // Isv的访问Key，来自于Bss
    private String service;             // 服务类型（1:普通短信, 2:可上行的短信）
    private int level = 1;              // 用户级别（1:第一级, 2:第二级, 3:第三级, 4:第四级）
    private int state = 1;              // 用户状态（-1:不可用, 1:可用）
    private Date createTime;            // 创建时间
    private Date updateTime;            // 更新时间
    
    
    public User() { }
    
    @Column(name = "bss_app_id", length = 36, nullable = true)
    public String getBssAppId() {
        return bssAppId;
    }

    public void setBssAppId(String bssAppId) {
        this.bssAppId = bssAppId;
    }
    
    @Column(name = "bss_org_id", length = 36, nullable = true)
    public String getBssOrgId() {
        return bssOrgId;
    }

    public void setBssOrgId(String bssOrgId) {
        this.bssOrgId = bssOrgId;
    }

    @Column(name = "bss_user_id", length = 36, nullable = false)
    public String getBssUserId() {
        return bssUserId;
    }

    public void setBssUserId(String bssUserId) {
        this.bssUserId = bssUserId;
    }

    @Column(name = "bss_app_key", length = 36, nullable = false)
    public String getBssAppKey() {
        return bssAppKey;
    }

    public void setBssAppKey(String bssAppKey) {
        this.bssAppKey = bssAppKey;
    }
    
//    @Column(name = "bss_app_secret", nullable = false)
//    public String getBssAppSecret() {
//        return bssAppSecret;
//    }
//
//    public void setBssAppSecret(String bssAppSecret) {
//        this.bssAppSecret = bssAppSecret;
//    }
    
    @Column(name = "service", length = 32, nullable = false)
    public String getService() {
        return service;
    }
    
    public void setService(String service) {
        this.service = service;
    }
    
    @Column(name = "level", length = 2, nullable = false)
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
//    @Column(name = "billing_type", nullable = false)
//    public int getBillingType() {
//        return billingType;
//    }
//    
//    public void setBillingType(int billingType) {
//        this.billingType = billingType;
//    }
    
    @Column(name = "state", length = 2, nullable = false)
    public int getState() {
        return state;
    }
    
    public void setState(int state) {
        this.state = state;
    }
    
//    @Column(name = "extend_code", nullable = true)
//    public String getExtendCode() {
//        return extendCode;
//    }
//    
//    public void setExtendCode(String extendCode) {
//        this.extendCode = extendCode;
//    }
    
    @Column(name = "create_time", nullable = false)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "update_time", nullable = true)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    
}
