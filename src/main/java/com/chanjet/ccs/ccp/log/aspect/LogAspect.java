package com.chanjet.ccs.ccp.log.aspect;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.chanjet.ccs.ccp.base.util.CcpTimingLog;

@Component
@Aspect
public class LogAspect {
	private static final Log timingLog = LogFactory.getLog(CcpTimingLog.class);

	@Around("within(@org.springframework.stereotype.Controller *)")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();  
        Object obj = null;
        long startTime = new Date().getTime();
        try {  
            obj = joinPoint.proceed(args);
        } catch (Throwable e) {
            e.printStackTrace();  
        }
        long endTime = new Date().getTime();
        long time = endTime - startTime;
        
        StringBuilder logInfoBuilder = new StringBuilder();
        logInfoBuilder.append(joinPoint.getTarget().getClass().getName() + " ");
        logInfoBuilder.append(joinPoint.getSignature().getName() + " ");
        logInfoBuilder.append(startTime + " ");
        logInfoBuilder.append(endTime + " ");
        logInfoBuilder.append(time);
        timingLog.info(logInfoBuilder.toString());
        return obj;
	}
	
}
