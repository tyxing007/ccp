package com.chanjet.ccs.base.util;

import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.ResourceBundle;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chanjet.ccs.base.basic.Constants;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CommonUtil {
	
private static Log logger = LogFactory.getLog(CommonUtil.class);
    
    private static String errorInfo = "map=>bean赋值失败";
    
    public static Object getBean(Object bean, Map properties) {
        try {
            BeanUtils.populate(bean, properties);
        } catch (IllegalAccessException e) {
            logger.error(errorInfo, e);
            return null;
        } catch (InvocationTargetException e) {
            logger.error(errorInfo, e);
            return null;
        }
        return bean;
    }
    
    // 从配置文件中获取配置项
    public static String getConfigContent(String configName, String key){
        ResourceBundle resource = ResourceBundle.getBundle(configName);
        return resource.getString(key) == null ? "" : resource.getString(key);
    }
    
    // 字符串判空
    public static boolean isEmpty(String s) {
        if (s == null || s.trim().equals(""))
            return true;
        return false;
            
    }
    
    // 字符串非空判定
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }
    
    // 拼凑请求参数串  param=value&param=value...
    public static String getParamsString(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            sb.append(key).append(Constants.EQUAL_SIGN).append(params.get(key)).append(Constants.AND);
        }
        String s = sb.toString();
        return s.substring(0, s.length() - 1);
    }
    
    
    
    private static String HMACSHA1 = "HmacSHA1";
    /**
     * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
     * 
     * @param input
     *            原始输入字符数组
     * @param key
     *            HMAC-SHA1密钥
     */
    public static byte[] hmacSha1(byte[] input, byte[] key) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, HMACSHA1);
            Mac mac = Mac.getInstance(HMACSHA1);
            mac.init(secretKey);
            return mac.doFinal(input);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    
}
