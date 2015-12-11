package com.chanjet.ccs.ccp.base.entity;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class ResultDataSms {

    private String smsId;           // 短信ID
    private String billingNo;       // 扣费流水号
    private String billingCount;    // 创建短信成功后，本次扣费额

    public ResultDataSms() { }
    
    public ResultDataSms(int smsId, String billingNo, int billingCount) {
        this.smsId = String.valueOf(smsId);
        this.billingNo = billingNo;
        this.billingCount = String.valueOf(billingCount);
    }
    
    public ResultDataSms(int smsId, String billingNo, double billingCount) {
        this.smsId = String.valueOf(smsId);
        this.billingNo = billingNo;
        this.billingCount = String.valueOf(billingCount);
    }
    
    public ResultDataSms(String smsId, String billingNo, String billingCount) {
        this.smsId = smsId;
        this.billingNo = billingNo;
        this.billingCount = billingCount;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getBillingNo() {
        return billingNo;
    }

    public void setBillingNo(String billingNo) {
        this.billingNo = billingNo;
    }

    public String getBillingCount() {
        return billingCount;
    }

    public void setBillingCount(String billingCount) {
        this.billingCount = billingCount;
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
