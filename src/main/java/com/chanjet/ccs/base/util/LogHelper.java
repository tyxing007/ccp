package com.chanjet.ccs.base.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogHelper {
    
    private static Log logger = LogFactory.getLog(LogHelper.class);
    
    public static void log(Log logger, Object info, Exception ex) {
        if (logger.isDebugEnabled()) {
            logger.debug(info);
        } else if (logger.isInfoEnabled()) {
            logger.info(info);
        } else if (logger.isWarnEnabled()) {
            logger.warn(info);
        } else if (logger.isErrorEnabled()) {
            logger.error(info, ex);
        } else if (logger.isFatalEnabled()) {
            logger.fatal(info, ex);
        }
    }
    
    public static void log(Object result) {
        log(logger, result, null);
    }
    
    public static Object logAndReturn(Log logger, Object result, Exception ex) {
        log(logger, result, ex);
        return result;
    }
    
}
