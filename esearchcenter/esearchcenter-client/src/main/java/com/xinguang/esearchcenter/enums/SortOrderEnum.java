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
public enum SortOrderEnum {
	//升序
	ASC(0, "ascending order"), 
	
	//降序
	DESC(1, "descending order");
 
	/**
	 * 返回状态码
	 */
	private int statusCode;
	
	/**
	 * 返回状态信息
	 */
	private String statusMsg;
 
	SortOrderEnum(int statusCode, String statusMsg) {
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
