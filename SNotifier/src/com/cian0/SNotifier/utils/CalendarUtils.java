package com.cian0.SNotifier.utils;

import java.util.Calendar;

public class CalendarUtils {
	public static String calendarToString (Calendar cal){
		if (cal == null)
			return null;
		StringBuilder sb = new StringBuilder();
		sb.append(cal.get(Calendar.YEAR));
		sb.append("-");
		sb.append(cal.get(Calendar.MONTH) + 1);
		sb.append("-");
		sb.append(cal.get(Calendar.DATE));
		sb.append(" ");
		sb.append(cal.get(Calendar.HOUR_OF_DAY));
		sb.append(":");
		sb.append(cal.get(Calendar.MINUTE));
		sb.append(":");
		sb.append(cal.get(Calendar.SECOND));
		
		
		return sb.toString();
	}
	
	public static Calendar stringToCalendar (String time){
		
		String timeStamp[] = time.split("\\ ");
		Calendar cal = Calendar.getInstance();
		
		if (timeStamp.length >= 2){
			String dateArr[] = timeStamp[0].split("\\-");
			cal.set(Calendar.YEAR, Integer.valueOf(dateArr[0]));
			cal.set(Calendar.MONTH, Integer.valueOf(dateArr[1]) - 1);
			cal.set(Calendar.DATE, Integer.valueOf(dateArr[2]));
			
			String timeArr[] = timeStamp[1].split("\\:");
			cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeArr[0]));
			cal.set(Calendar.MINUTE, Integer.valueOf(timeArr[1]));
			cal.set(Calendar.SECOND, Integer.valueOf(timeArr[2]));
			
		}else{
			return null;
		}
		return cal;
	}
}
