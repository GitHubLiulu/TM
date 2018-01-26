package com.ai.ecs.common.utils.ftp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static String toString(Date date, String pattern){
		if (date == null){
			date = new Date(System.currentTimeMillis());
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern);
		return sdf.format(date);
	}
	
	public static String toString(Timestamp date, String pattern){
		if (date == null){
			date = new Timestamp(System.currentTimeMillis());
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern);
		return sdf.format(date);
	}
}
