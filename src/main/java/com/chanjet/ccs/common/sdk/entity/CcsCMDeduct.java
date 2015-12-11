package com.chanjet.ccs.common.sdk.entity;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class CcsCMDeduct {

    private String bssAppId;            // 应用的唯一标识，来自于Bss
    private String bssOrgId;            // 企业的唯一标识，来自于Bss
    private String ccsID;               // 公共服务ID // 邮件 // 短信 // 天气
    private String ccsSubServiceID;     // 服务。对应于ConstantsCcs中的SERV_*
    private long amount;                // 消费数量
    private String dimensions;          // 关维度，比如oss中的请求量、存储空间多少

    public String getBssAppId() {
        return bssAppId;
    }

    public void setBssAppId(String bssAppId) {
        this.bssAppId = bssAppId;
    }

    public String getBssOrgId() {
        return bssOrgId;
    }

    public void setBssOrgId(String bssOrgId) {
        this.bssOrgId = bssOrgId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCcsID() {
        return ccsID;
    }

    public void setCcsID(String ccsID) {
        this.ccsID = ccsID;
    }

    public String getCcsSubServiceID() {
        return ccsSubServiceID;
    }

    public void setCcsSubServiceID(String ccsSubServiceID) {
        this.ccsSubServiceID = ccsSubServiceID;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
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
