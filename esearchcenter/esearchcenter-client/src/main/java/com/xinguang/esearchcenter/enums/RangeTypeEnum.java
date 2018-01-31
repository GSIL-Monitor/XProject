/**
 * Project Name:esearchcenter-client
 * File Name:SortEnum.java
 * Package Name:com.xinguang.esearchcenter
 * Date:2016年11月15日上午10:23:57
 * Copyright (c) 2016, js.xiupin@list.nie.netease.com All Rights Reserved.
 *
*/

package com.xinguang.esearchcenter.enums;

/**
 * ClassName:SortEnum <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年11月15日 上午10:23:57 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
public enum RangeTypeEnum {
	//全有
	BOTH(0, "top and bottom limit"),
	
	//只有上限
	TOP(1, "upper limit"), 
	
	//只有下限
	Bottom(2, "lower limit");

	private int statusCode;

	private String statusMsg;
 
	RangeTypeEnum(int statusCode, String statusMsg) {
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
	}
 
	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}
 
	/**
	 * @return the statusMsg
	 */
	public String getStatusMsg() {
		return statusMsg;
	}
}
