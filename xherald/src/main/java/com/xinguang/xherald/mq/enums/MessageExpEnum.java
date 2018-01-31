package com.xinguang.xherald.mq.enums;

public enum MessageExpEnum {
	
	Mes_1(1, "消息格式有误"),
	Mes_2(2, "消息处理有误");

 
	/**
	 * 返回状态码
	 */
	private int statusCode;
	
	/**
	 * 返回状态信息
	 */
	private String statusMsg;
 
	MessageExpEnum(int statusCode, String statusMsg) {
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