package com.chanjet.ccs.ccp.base.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 读取配置文件
 * 
 */
public class Configure {
    
    private static Log log = LogFactory.getLog(Configure.class);
    
    private static String BUNDLE_NAME = "datasource";
    private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
    private static final String info = "没有找到对应的键值  : key = ";

    
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key).trim();
        } catch (MissingResourceException ex) {
            log.error(info + key, ex);
            return null;
        }
    }

    
    public static int parseInt(String key) {
        try {
            return NumberUtils.toInt(Configure.getString(key));
        } catch (Exception ex) {
            log.error(info + key, ex);
        }
        return 0;
    }
    
    
    public static int parseInt(String key, int defaultValue, Integer min, Integer max) {
        int value = defaultValue;
        try {
            value = Integer.parseInt(Configure.getString(key));
            if((min != null && value < min) || (max != null && value > max))
                value = defaultValue;
        } catch (Exception ex) {
            log.error(info + key, ex);
            value = defaultValue;
        }
        return value;
    }
    
    
    public static boolean parseBool(String key) {
        return BooleanUtils.toBoolean(Configure.getString(key));
    }
    
    
    
    // TODO 检查
    public static synchronized void reload() {
        ResourceBundle.clearCache();
        Configure.RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
    }
    
    public static void main(String[] args) {
        String s = "true";
        System.out.println(BooleanUtils.toBoolean("true"));
    }
}
