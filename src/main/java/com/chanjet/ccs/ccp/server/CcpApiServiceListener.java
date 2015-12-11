package com.chanjet.ccs.ccp.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CcpApiServiceListener implements ServletContextListener {

    static Log log = LogFactory.getLog(CcpApiServiceListener.class);
    
    // TODO 创建黑关键字树
    public void contextInitialized(ServletContextEvent arg0) {
        log.info("tomcat starting......");
    }
    
    public void contextDestroyed(ServletContextEvent arg0) {
        log.info("tomcat stopping......");
    }

}
