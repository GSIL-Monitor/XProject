package com.xinguang.xusercenter.common.base;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回的数据内容
 * 
 * @author yangsh
 *
 */
@Getter
@Setter
public class ReturnData {

	private String code; // 编码
	private String desc; // 描述
	private Map<String, Object> data; // 内容

	public ReturnData() {}
	
	public ReturnData(ReturnCode returnCode) {
		this.code = returnCode.getCode();
		this.desc = returnCode.getDesc();
		this.data = new HashMap<>();
	}
	
	public ReturnData(ReturnCode returnCode, Map<String, Object> data) {
		this.code = returnCode.getCode();
		this.desc = returnCode.getDesc();
		if (data == null) {
			this.data = new HashMap<>();
		} else {
			this.data = data;
		}
	}
	
}
