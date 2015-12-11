package com.chanjet.ccs.base.entity;

import java.io.IOException;
import java.io.Serializable;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * 返回结果
 * @Date 2013-7-30 下午1:37:31
 *
 */

public class Result implements Serializable {       

	private static final long serialVersionUID = -5322947431757021802L;
	private int httpCode;       // http响应码
    private String code;        // 自定义的信息编码
    private String info;        // 请求的业务数据或错误提示信息

    public Result() { }
    
    public Result(int httpCode, String code, String info) {
        this.httpCode = httpCode;
        this.code = code;
        this.info = info;
    }

    public Result copy(){
        Result result = new Result();
        result.setHttpCode(httpCode);
        result.setCode(this.code);
        result.setInfo(this.info);
        return result;
    }
    
    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
