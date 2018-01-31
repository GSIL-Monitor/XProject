package com.xinguang.xherald.mq.utils;

import com.alibaba.druid.filter.config.ConfigTools;

public class DecryptMethod {
	
	public static String getDecryptValue(String decryptValue){
		
		try {
			String value = ConfigTools.decrypt(decryptValue);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
