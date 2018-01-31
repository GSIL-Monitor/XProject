package com.xinguang.xherald.common.utils;

import java.util.UUID;

public class PublicUtil {
	public static String genUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String genSourceUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}
