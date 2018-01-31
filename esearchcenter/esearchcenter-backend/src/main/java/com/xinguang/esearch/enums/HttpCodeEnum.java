package com.xinguang.esearch.enums;
public enum HttpCodeEnum {
	HTTPCODE_200(200, "Sucess"),
	HTTPCODE_204(204,  "No Content"),
	HTTPCODE_400(400, "Client Error"), 
	HTTPCODE_500(500, "Server Error");
 
	/**
	 * 返回状态码
	 */
	private int statusCode;
	
	/**
	 * 返回状态信息
	 */
	private String statusMsg;
 
	HttpCodeEnum(int statusCode, String statusMsg) {
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