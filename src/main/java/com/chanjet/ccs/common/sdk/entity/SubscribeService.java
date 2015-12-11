package com.chanjet.ccs.common.sdk.entity;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class SubscribeService {

    private String subScribeServiceId; // 子服务充值订单号
    private String parentId; // 属于哪个订单号
    private String ccsSubServiceID; // 子服务id
    private List<SubServiceDetail> detailList;
    private Timestamp expires; // 可选 有效期
    private Timestamp createDateTime; // 记录的创建时间
    private Timestamp updateDateTime; // 记录的最后更新时间

    public String getSubScribeServiceId() {
        return subScribeServiceId;
    }

    public void setSubScribeServiceId(String subScribeServiceId) {
        this.subScribeServiceId = subScribeServiceId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<SubServiceDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<SubServiceDetail> detailList) {
        this.detailList = detailList;
    }

    public String getCcsSubServiceID() {
        return ccsSubServiceID;
    }

    public void setCcsSubServiceID(String ccsSubServiceID) {
        this.ccsSubServiceID = ccsSubServiceID;
    }

    public Timestamp getExpires() {
        return expires;
    }

    public void setExpires(Timestamp expires) {
        this.expires = expires;
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
