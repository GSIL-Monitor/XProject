package com.xinguang.xusercenter.common.util;

/**
 * 字符串工具类
 * 
 * @author yangsh
 *
 */
public class StringUtil {

	public static boolean isNull(String s) {
		return s == null;
	}
	
	public static boolean isNotNull(String s) {
		return s != null;
	}
	
	public static boolean isEmpty(String s) {
		if (s == null) {
			return true;
		}
		
		return s.trim().length() == 0;
	}
	
	public static boolean isNotEmpty(String s) {
		if (s == null) {
			return false;
		}
		
		return s.trim().length() > 0;
	}
	
	public static boolean isEquals(String s1, String s2) {
		if (s1 == null) {
			if (s2 == null) {
				return true;
			}
			return false;
		}

		return s1.equals(s2);
	}
	
}
