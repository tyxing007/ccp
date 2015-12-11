package com.chanjet.ccs.common.sdk.service;

import org.apache.commons.lang3.StringUtils;

import com.chanjet.ccs.common.sdk.auth.AuthGenerator;
import com.chanjet.ccs.common.sdk.base.CcsCMConstants;
import com.chanjet.ccs.common.sdk.base.CcsCMResult;
import com.chanjet.ccs.common.sdk.entity.CcsCMDeduct;
import com.chanjet.ccs.common.sdk.util.DataWraper;
import com.chanjet.ccs.common.sdk.util.HttpUtil;

public class CcsCMUtil {

    
    // 扣费
    public static CcsCMResult deduct(String serviceKey, String serviceSecret, CcsCMDeduct deduct) {
        try {
            String authorization = AuthGenerator.getAuth(deduct, serviceKey, serviceSecret);
            Object result = HttpUtil.getResultObj(CcsCMConstants.urlDeduct, authorization);
            return wrapResult(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    // 回滚费用
    public static CcsCMResult robackDeduct(String serviceKey, String serviceSecret, String deductNo, String robackValue) {
        String url = CcsCMConstants.urlRollback + CcsCMConstants.QUES_MARK;
        String queryStr = CcsCMConstants.kDeductNo + CcsCMConstants.EQUAL_SIGN + deductNo;
        queryStr += CcsCMConstants.AND + CcsCMConstants.kRobackValue + CcsCMConstants.EQUAL_SIGN + robackValue;
        url += queryStr;
        try {
            String authorization = AuthGenerator.getAuth(serviceKey, serviceSecret);
            String json = HttpUtil.get(url, authorization);
            CcsCMResult result = (CcsCMResult)DataWraper.wrapJson2Object(json, CcsCMResult.class);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    // 获取余量
    public static CcsCMResult getServiceAmount(String serviceKey, String serviceSecret, String ccsId, String orgId) {
        return getServiceAmount(serviceKey, serviceSecret, ccsId, null, orgId);
    }
    
    // 获取余量
    public static CcsCMResult getServiceAmount(String serviceKey, String serviceSecret, String ccsId, String subServiceId, String orgId) {
        String url = CcsCMConstants.urlAmount + CcsCMConstants.QUES_MARK;
        String queryStr = CcsCMConstants.kCcsId + CcsCMConstants.EQUAL_SIGN + ccsId;
        queryStr += CcsCMConstants.AND + CcsCMConstants.kOrgId + CcsCMConstants.EQUAL_SIGN + orgId;
        if (StringUtils.isNotEmpty(subServiceId))
            queryStr += CcsCMConstants.AND + CcsCMConstants.kSubServiceId + CcsCMConstants.EQUAL_SIGN + subServiceId;
        url += queryStr;
        try {
            String authorization = AuthGenerator.getAuth(serviceKey, serviceSecret);
            Object result = HttpUtil.getResultObj(url, authorization);
            return wrapResult(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    // 增加订阅
    public static void addSubscription(String authorization) {
        // TODO
    }
    
    // 取消订阅
    public static void unSubscription(String authorization) {
        // TODO
    }
    
    private static CcsCMResult wrapResult(Object result) {
        if (result instanceof CcsCMResult)
            return (CcsCMResult)result;
        return new CcsCMResult(200, "success", (String)result);
    }
    
}
