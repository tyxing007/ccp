package com.chanjet.ccs.common.sdk.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtil {
	public static final String DATETIME_FORMATE = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMATE = "yyyy-MM-dd";
	
	/////////////////////
	// 日期对象类型转换
	/////////////////////

	public static Date strToDate(String date) {
		DateFormat formaterd = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formaterd.parse(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date strToDate(String date, String format) {
		DateFormat formaterd = new SimpleDateFormat(format);
		try {
			return formaterd.parse(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String dateToStr(Date date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String dateToStr(Date date, String format) {
		try {
			return new SimpleDateFormat(format).format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String dateToAllStr(Date date) {
		return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static Date strToAllDate(String date) {
		return strToDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	
	///////////////////////////////////
	// 获取或设置日期数据
	//////////////////////////////////
	
	public static void setHour(Date date, int hour) {
		GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(date);
        gCal.set(Calendar.HOUR_OF_DAY, hour);
        date.setTime(gCal.getTime().getTime());
	}
	
	public static void setMinute(Date date, int minute) {
		GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(date);
        gCal.set(Calendar.MINUTE, minute);
        date.setTime(gCal.getTime().getTime());
	}
	
	public static void setSecond(Date date, int second) {
		GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(date);
        gCal.set(Calendar.SECOND, second);
        date.setTime(gCal.getTime().getTime());
	}
	
	public static List<Date> getCurrentMonth(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);  
		int month = cal.get(Calendar.MONTH) + 1;  
		boolean isLeapYear = year % 4 == 0 && (year % 400 == 0 || year % 100 != 0);
		String lastDay = "-31";
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			lastDay = "-30";
		}
		else if (month == 2) {
			lastDay = (isLeapYear ? "-29" : "-28");
		}
		List<Date> list = new ArrayList<Date>();
		list.add(DateUtil.strToDate(year + "-" + month + "-01"));
		list.add(DateUtil.strToDate(year + "-" + month + lastDay));
		return list;
	}

	/**
	 * 返回与今天差异num天的日期 格式"yyyy-MM-dd"
	 */
	public static String getDateStrDiff(int days, Date date) {
		SimpleDateFormat df = new SimpleDateFormat(DateUtil.DATETIME_FORMATE);
	    return df.format(DateUtil.getDateDiff(days, date));
	    
		/*Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String lastDate = df.format(new Date(d.getTime() + num * 24 * 60 * 60 * 1000));
		return lastDate;*/
	}
	
	/**
	 * 返回与今天差异num天的日期 格式"yyyy-MM-dd"
	 */
	public static Date getDateDiff(int days, Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + days);
	    return calendar.getTime();
	    
		/*Date d = new Date();
		return new Date(d.getTime() + num * 24 * 60 * 60 * 1000);*/
	}
	
	/**
	 * 返回头两个大写字母的周几
	 */
    public static String getEnDay(int weekDay) {
    	String result = "";
    	switch (weekDay) {
		case 1:
			result = "MO";
			break;
		case 2:
			result = "TU";
			break;
		case 3:
			result = "WE";
			break;
		case 4:
			result = "TH";
			break;
		case 5:
			result = "FR";
			break;
		case 6:
			result = "SA";
			break;
		case 0:
			result = "SU";
			break;
		default:
			break;
		}
    	return result;
    }
    
    /**    
     * 得到本月的第一天 
     */     
    public static String getMonthFirstDay(boolean withTime) {      
        Calendar calendar = Calendar.getInstance();      
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        String result = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        if (withTime) {
        	result += " 00:00:00";
        }
        return result;
    }
         
    /**    
     * 得到本月的最后一天   
     */     
    public static String getMonthLastDay(boolean withTime) {
        Calendar calendar = Calendar.getInstance();      
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));      
        String result = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        if (withTime) {
        	result += " 23:59:59";
        }
        return result;
    }
	
	/////////////////////
	// 日期运算
	/////////////////////
	
	public static Date addDays(String startDate, String dateFormat, int days) {
        return addDays(strToDate(startDate, dateFormat), days);
    }

	public static Date addMinutes(Date startDate, int minutes) {
        GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(startDate);
        gCal.add(Calendar.MINUTE, minutes);

        return gCal.getTime();
    }
	
	public static Date addHours(Date startDate, int hours) {
	    GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(startDate);
        gCal.add(Calendar.HOUR, hours);

        return gCal.getTime();
	}
	
    public static Date addDays(Date startDate, int days) {
        GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(startDate);
        gCal.add(Calendar.DATE, days);

        return gCal.getTime();
    }
    
    public static Date addMonths(Date startDate, int months) {
        GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(startDate);
        gCal.add(Calendar.MONTH, months);

        return gCal.getTime();
    }
    
    public static Date addYears(Date startDate, int years) {
        GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(startDate);
        gCal.add(Calendar.YEAR, years);

        return gCal.getTime();
    }
}
