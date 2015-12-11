package com.chanjet.ccs.common.sdk.entity;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Subscription {

    private String subScriptionId;      // 订单记录id
    private String bssOrgId;            // 企业ID(UUID)
    private String bssServiceId;        // 应用中涉及的服务(如云存储中涉及 空间、流量、调用次数)
    private String bssSubscribeId;      // 用户在BSS中购买服务的标识（比如什么套餐）
    private String ccsID;               // 公共服务应用ID
    private Timestamp createDateTime;   // 记录的创建时间
    private Timestamp updateDateTime;   // 记录的最后更新时间
    private List<SubscribeService> subServiceList;

    public List<SubscribeService> getSubServiceList() {
        return subServiceList;
    }

    public void setSubServiceList(List<SubscribeService> subServiceList) {
        this.subServiceList = subServiceList;
    }

    public String getSubScriptionId() {
        return subScriptionId;
    }

    public void setSubScriptionId(String subScriptionId) {
        this.subScriptionId = subScriptionId;
    }

    public String getBssOrgId() {
        return bssOrgId;
    }

    public void setBssOrgId(String bssOrgId) {
        this.bssOrgId = bssOrgId;
    }

    public String getBssServiceId() {
        return bssServiceId;
    }

    public void setBssServiceId(String bssServiceId) {
        this.bssServiceId = bssServiceId;
    }

    public String getBssSubscribeId() {
        return bssSubscribeId;
    }

    public void setBssSubscribeId(String bssSubscribeId) {
        this.bssSubscribeId = bssSubscribeId;
    }

    public String getCcsID() {
        return ccsID;
    }

    public void setCcsID(String ccsID) {
        this.ccsID = ccsID;
    }

    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Timestamp getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Timestamp updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper(); 
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
