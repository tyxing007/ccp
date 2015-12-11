package com.chanjet.ccs.ccp.base.entity;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.chanjet.ccs.base.util.JsonDateTimeFormater;

public class ResultDataDeliver {

    private String mobile;              // 获取短信上行数据成功后，返回值中手机号的标识
    private String content;             // 获取短信上行数据成功后，返回值中短信内容的标识
    @JsonSerialize(using = JsonDateTimeFormater.class)
    private Date createTime;            // 上行短信创建时间

    public ResultDataDeliver() { }
    
    public ResultDataDeliver(String mobile, String content, Date createTime) {
        this.mobile = mobile;
        this.content = content;
        this.createTime = createTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
