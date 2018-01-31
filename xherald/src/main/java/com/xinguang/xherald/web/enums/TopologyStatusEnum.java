package com.xinguang.xherald.web.enums;


public enum TopologyStatusEnum {
	/*参数校验错误*/
	STATUS_0(0, "Original"),
	STATUS_1(1, "Create MQ Error"),
	STATUS_2(2, "Create ES Error"), 
	STATUS_3(3, "Create Monitor Error"),
	STATUS_4(4, "Success"),
	STATUS_5(5, "Invalid");
	
	/**
	 * 返回状态码
	 */
	private int statusCode;
	
	/**
	 * 返回状态信息
	 */
	private String statusMsg;
 
	TopologyStatusEnum(int statusCode, String statusMsg) {
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
	
	public static String getStatusMsg(int code){
		for (TopologyStatusEnum hce : TopologyStatusEnum.values()) {
			if (hce.getStatusCode() == code){
				return hce.getStatusMsg();
			}
		}
		return null;
	}
}