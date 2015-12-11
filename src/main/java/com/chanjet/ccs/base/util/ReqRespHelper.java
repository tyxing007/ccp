package com.chanjet.ccs.base.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import com.chanjet.ccs.base.basic.Constants;
import com.chanjet.ccs.base.basic.ConstantsHttp;
import com.chanjet.ccs.base.entity.Result;

public class ReqRespHelper {
    
    public static Result setResult(HttpServletResponse response, Result result) {
        //LogHelper.log(result);
        response.setStatus(result.getHttpCode());
        return result;
        
    }
    
    // 向response中放入返回值（JSON格式）
    public static boolean setAndWriteResult(HttpServletResponse response, Result result) throws IOException{
        //LogHelper.log(result);
        response.setStatus(result.getHttpCode());
        response.setCharacterEncoding(Constants.UTF8);         
        response.setContentType("application/json; charset=utf-8");      

        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.close();
        return result.getHttpCode() == ConstantsHttp.hc200;
    }
    
    // 向请求中放入请求参数（Attribute）
    public static void setAttrsToReq(HttpServletRequest request, Map<String, String> params) {
        for(String key : params.keySet()) {
            request.setAttribute(key, params.get(key));
        }
    }
    
    // 拼凑请求参数串  ?param=value&param=value...
    public static String getParamsString(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.QUES_MARK);
        for (String key : params.keySet()) {
            sb.append(key).append(Constants.EQUAL_SIGN).append(params.get(key)).append(Constants.AND);
        }
        String s = sb.toString();
        return s.substring(0, s.length() - 1);
    }
    
    // 从请求中获取参数
    public static Map<String, String> getParamsFromReq(HttpServletRequest request) {
        Map<String, String> result = new HashMap<String, String>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            result.put(param, StringUtils.trimToEmpty(request.getParameter(param)));
        }
            return result;
    }
    
    // 从请求中获取参数
    public static Map<String, Object> getAttrsFromReq(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        Enumeration<String> attrs = request.getAttributeNames();
        while (attrs.hasMoreElements()) {
            String attr = attrs.nextElement();
            result.put(attr, request.getAttribute(attr));
        }
            return result;
    }
    
    
    
}
