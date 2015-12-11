package com.chanjet.ccs.base.util;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

/*
 * 自定义的DailyRollingFileAppender
 * 只将匹配级别的日志写入相应文件，而不是将级别大于Threshold的全写入文件
 */
public class CcsLogAppender extends DailyRollingFileAppender {
	@Override
	public boolean isAsSevereAsThreshold(Priority priority) {
		//return ((threshold == null) || priority.isGreaterOrEqual(threshold);
		return this.getThreshold().equals(priority);  
	}
}