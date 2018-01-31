package com.dangdang.ddframe.job.lite.console.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author yangsh
 *
 */
public class DateUtil {

	/**
	 * 获取相差秒数
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long getSecDuration(String startTime, String endTime) {
		long secDuration = 0;
		if (startTime == null || startTime == null) {
			return secDuration;
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date start = df.parse(startTime);
			Date end = df.parse(endTime);
			
			secDuration = (end.getTime() - start.getTime()) / 1000;
		} catch (Exception e) {
		}

		return secDuration;
	}

}
