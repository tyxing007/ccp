package com.chanjet.ccs.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.chanjet.ccs.base.basic.QueryNames;
import com.chanjet.ccs.ccp.base.basic.EnumValues;

/**
 * 
 * @Date 2013-7-25 上午9:37:10
 */

@Entity
@Table(name = "t_app")
@NamedQueries({ 
	@NamedQuery(name = QueryNames.appListByAppKey, 
	            query = " SELECT app FROM App app WHERE app.bssAppKey = ? " +
                        " AND app.status = " + EnumValues.STATUS_ENABLE)
})
public class App extends BaseEntity {
    
    private String bssAppId;        // 应用的唯一标识，来自于Bss
    private String bssAppKey;       // 应用的访问Key，来自于Bss
    private String bssAppSecret;    // 应用的访问密钥，来自于Bss
    private String bssIsvId;        // ISV的唯一标识，来自于Bss
    private int status = 1;         // App状态（-1:不可用, 1:可用）
    private int excodeType = 1;     // 扩展号类型（动态/静态）
    private int whoseExcode;        // 扩展号分配模式（使用谁的扩展号--1:应用的, 2:应用中企业的, 3:应用中某企业的用户的）
    private Date createDateTime;    // 创建时间
    private Date updateDateTime;    // 更新时间

    public App() { }

    @Column(name = "bss_app_id", length = 36, nullable = true)
    public String getBssAppId() {
        return bssAppId;
    }

    public void setBssAppId(String bssAppId) {
        this.bssAppId = bssAppId;
    }

    @Column(name = "bss_app_key", length = 36, nullable = true, unique = true)
    public String getBssAppKey() {
        return bssAppKey;
    }

    public void setBssAppKey(String bssAppKey) {
        this.bssAppKey = bssAppKey;
    }

    @Column(name = "bss_app_secret", length = 36, nullable = true)
    public String getBssAppSecret() {
        return bssAppSecret;
    }

    public void setBssAppSecret(String bssAppSecret) {
        this.bssAppSecret = bssAppSecret;
    }

    @Column(name = "bss_isv_id", length = 36, nullable = false)
    public String getBssIsvId() {
        return bssIsvId;
    }

    public void setBssIsvId(String bssIsvId) {
        this.bssIsvId = bssIsvId;
    }

    @Column(name = "status", length = 2, nullable = false)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "excode_type", length = 2, nullable = false)
    public int getExcodeType() {
        return excodeType;
    }

    public void setExcodeType(int excodeType) {
        this.excodeType = excodeType;
    }

    @Column(name = "whose_excode", length = 2, nullable = false)
    public int getWhoseExcode() {
        return whoseExcode;
    }

    public void setWhoseExcode(int whoseExcode) {
        this.whoseExcode = whoseExcode;
    }

    @Column(name = "create_date_time", nullable = false)
    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    @Column(name = "update_date_time", nullable = true)
    public Date getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

}
