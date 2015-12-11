package com.chanjet.ccs.base.entity;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class ResultInner<T> {

    private boolean result;         // 执行结果（成功/不成功）
    private String resultKey;       // 应当对应于EnumResult中的Key
    private T data;                 // 执行成功的返回的业务数据

    public ResultInner() { }
    
    public ResultInner(boolean result) {
        this.result = result;
    }
    
    // 执行不成功
    public ResultInner(String resultKey) {
        this.result = false;
        this.resultKey = resultKey;
    }
    
    // 执行成功
    public ResultInner(String resultKey, T data) {
        this.result = true;
        this.resultKey = resultKey;
        this.data = data;
    }
    
    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getResultKey() {
        return resultKey;
    }

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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
