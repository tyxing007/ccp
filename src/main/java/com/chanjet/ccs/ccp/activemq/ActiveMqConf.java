package com.chanjet.ccs.ccp.activemq;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ActiveMqConf {
    static Log log = LogFactory.getLog(ActiveMqConf.class);
    
    private static final String BUNDLE_NAME = "amqconf";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private ActiveMqConf() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key).trim();
        } catch (MissingResourceException e) {
            log.error("cannot find key : " + key + "!");
            return null;
        }
    }
    
    public static void main(String[] args) {
        
    }
}
