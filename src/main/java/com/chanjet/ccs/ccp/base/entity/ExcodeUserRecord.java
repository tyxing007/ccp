package com.chanjet.ccs.ccp.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.chanjet.ccs.base.basic.QueryNames;
import com.chanjet.ccs.base.entity.BaseEntity;
import com.chanjet.ccs.ccp.base.basic.EnumValues;

@Entity
@Table(name = "t_excode_user_record")
@NamedQueries({ 
    @NamedQuery(name = QueryNames.excodeUserListByApp, 
                query = " SELECT eur FROM ExcodeUserRecord eur WHERE eur.type = ? AND eur.whose = " + EnumValues.EXCODE_APP + 
                        " AND eur.business = ? AND eur.bssAppKey = ? AND eur.status = " + EnumValues.STATUS_ENABLE +
                        " AND eur.bssUserId IS NULL AND eur.bssOrgId IS NULL "),
    @NamedQuery(name = QueryNames.excodeUserListByAppOrg, 
                query = " SELECT eur FROM ExcodeUserRecord eur WHERE eur.type = ? AND eur.whose = " + EnumValues.EXCODE_APP_ORG + 
                        " AND eur.business = ? AND eur.bssAppKey = ?  AND eur.bssOrgId = ? AND eur.status = " + 
                        EnumValues.STATUS_ENABLE + " AND eur.bssUserId IS NULL "),
    @NamedQuery(name = QueryNames.excodeUserListByAppOrgUser, 
                query = " SELECT eur FROM ExcodeUserRecord eur WHERE eur.type = ? AND eur.whose = " + EnumValues.EXCODE_APP_USER + 
                        " AND eur.business = ? AND eur.bssAppKey = ?  AND eur.bssOrgId = ?  AND eur.bssUserId = ? " +
                        " AND eur.status = " + EnumValues.STATUS_ENABLE)
})
public class ExcodeUserRecord extends BaseEntity {

    private String codeName;        // 扩展号
    private int type;               // 固定分配\随机分配
    private int whose;              // 应用的，用户的，企业的
    private String business = "";   // 业务模块
    private String bssUserId = "";  // 用户的唯一标识，来自于Bss
    private String bssOrgId = "";   // 企业的唯一标识，来自于Bss
    private String bssAppId;        // 应用的唯一标识，来自于Bss
    private String bssAppKey;       // 应用的访问Key，来自于Bss
    private Date startDateTime;     // 起始时间
    private Date endDateTime;       // 结束时间
    private int status;             // 状态：活跃/失效

    @Column(name = "code_name", unique = false, nullable = false)
    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    @Column(name = "type", nullable = false)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "whose", nullable = false)
    public int getWhose() {
        return whose;
    }

    public void setWhose(int whose) {
        this.whose = whose;
    }

    @Column(name = "business", nullable = false)
    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    @Column(name = "bss_user_id", nullable = false)
    public String getBssUserId() {
        return bssUserId;
    }

    public void setBssUserId(String bssUserId) {
        this.bssUserId = bssUserId;
    }

    @Column(name = "bss_org_id", nullable = false)
    public String getBssOrgId() {
        return bssOrgId;
    }

    public void setBssOrgId(String bssOrgId) {
        this.bssOrgId = bssOrgId;
    }

    @Column(name = "bss_app_id")
    public String getBssAppId() {
        return bssAppId;
    }

    public void setBssAppId(String bssAppId) {
        this.bssAppId = bssAppId;
    }

    @Column(name = "bss_app_key", nullable = true)
    public String getBssAppKey() {
        return bssAppKey;
    }

    public void setBssAppKey(String bssAppKey) {
        this.bssAppKey = bssAppKey;
    }

    @Column(name = "start_date_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    @Column(name = "end_date_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Column(name = "status", nullable = false)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
