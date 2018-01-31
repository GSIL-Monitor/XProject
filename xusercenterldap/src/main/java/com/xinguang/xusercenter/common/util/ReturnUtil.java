package com.xinguang.xusercenter.common.util;

import java.util.Map;

import com.xinguang.xusercenter.common.base.ReturnCode;
import com.xinguang.xusercenter.common.base.ReturnData;

/**
 * 接口调用返回统一操作
 * @author yangsh
 */
public class ReturnUtil {
	
	public static ReturnData fail(ReturnCode rc) {
		return new ReturnData(rc);
	}

	public static ReturnData fail(ReturnCode rc, Map<String, Object> data) {
		return new ReturnData(rc, data);
	}
	
	public static ReturnData success() {
		return new ReturnData(ReturnCode.SUCCESS);
	}

	public static ReturnData success(Map<String, Object> data) {
		return new ReturnData(ReturnCode.SUCCESS, data);
	}
	
}
