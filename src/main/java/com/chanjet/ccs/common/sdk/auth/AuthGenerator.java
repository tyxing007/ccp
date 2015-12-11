package com.chanjet.ccs.common.sdk.auth;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.chanjet.ccs.common.sdk.base.CcsCMConstants;
import com.chanjet.ccs.common.sdk.encryption.Cryptos;
import com.chanjet.ccs.common.sdk.encryption.Encodes;
import com.chanjet.ccs.common.sdk.util.DataWraper;
import com.chanjet.ccs.common.sdk.util.DateUtil;




// 公共服务请求commons服务时所需的Authorization
// {serviceKey:xx,paramInfo:xx,authInfo:xx}
public class AuthGenerator {
    
    // 获取Authorization（不带业务数据）
    public static <T> String getAuth(String serviceKey, String serviceSecret) throws Exception {
        String paramInfo = getParamInfo(null);
        String authInfo = getAuthInfo(paramInfo, serviceKey, serviceSecret);
        return getAuthorization(serviceKey, paramInfo, authInfo);
    }
    
    // 获取Authorization（带业务数据）
    public static <T> String getAuth(T obj, String serviceKey, String serviceSecret) throws Exception {
        String jsonStr = null;
        if (obj != null)
            jsonStr = getJsonStr(obj);
        String paramInfo = getParamInfo(jsonStr);
        String authInfo = getAuthInfo(paramInfo, serviceKey, serviceSecret);
        return getAuthorization(serviceKey, paramInfo, authInfo);
    }
    
    // jsonStr：业务数据
    private static String getParamInfo(String jsonStr){
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotEmpty(jsonStr))
            map.put(CcsCMConstants.kJsonStr, jsonStr);
        map.put(CcsCMConstants.kDate, getGmtDT());
        return DataWraper.wrap2Json(map);
    }
    
    private static String getAuthInfo(String paramInfo, String serviceKey, String serviceSecret) throws Exception {
        byte[] bytes = Cryptos.hmacSha1(paramInfo.getBytes(CcsCMConstants.UTF8), serviceSecret.getBytes());
        String authInfo = Encodes.encodeHex(bytes);
        return authInfo;
    }
    
    // 获取Authorization
    private static String getAuthorization(String serviceKey, String paramInfo, String authInfo) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(CcsCMConstants.kServiceKey, serviceKey);
        map.put(CcsCMConstants.kParamInfo, paramInfo);
        map.put(CcsCMConstants.kAuthInfo, authInfo);
        return Encodes.encodeBase64(DataWraper.wrap2Json(map).getBytes());
    }
    
    // 获取业务JSON串
    public static <T> String getJsonStr(T obj) {
        ObjectMapper mapper = new ObjectMapper(); 
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    private static String getGmtDT() {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        Date dateTime = Calendar.getInstance().getTime();
        return df.format(dateTime);
    }
}
