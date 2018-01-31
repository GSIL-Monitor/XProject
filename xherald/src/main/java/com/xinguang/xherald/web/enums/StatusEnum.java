
package com.xinguang.xherald.web.enums;
/**
 * 
 * ClassName: StatusEnum <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年10月12日 上午11:00:44 <br/>
 *
 * @author Administrator-lengxing
 * @version 
 * @since JDK 1.7
 */

public enum StatusEnum {
	//MQ状态枚举
	MQStatus_000(1000, "MQ request error"),
	MQStatus_001(1001, "connect rabbitmq cluster failed"),
	MQStatus_002(1101, "Exchange name already exists"),
	MQStatus_003(1102, "Queue name already exists"),
	MQStatus_004(1200, "MQ create failed"),
	MQStatus_005(1300, "MQ create success"),
	MQStatus_006(1400, "Create MQTopology exception"),
	MQStatus_007(1500, "MQTopology not exists"),
	
	//ES状态枚举
	ESStatus_000(2000, "Index request error"),
	ESStatus_001(2101, "Index already exist"),
	ESStatus_002(2102, "Type of index already exist"),
	ESStatus_003(2103, "Mapping of an index and type already exist"),
	ESStatus_007(2104, "ESIndex not exist"),
	ESStatus_004(2200, "Index create failed"),
	ESStatus_005(2300, "Index create success"),
	ESStatus_006(2400, "Create ESTopology exception"),
	
	//监听状态枚举
	LinstenerStatus_000(3200, "Listener create failed"),
	LinstenerStatus_001(3300, "Listener create success");
	
	/**
	 * 返回状态码
	 */
	private int statusCode;
	
	/**
	 * 返回状态信息
	 */
	private String statusMsg;
 
	StatusEnum(int statusCode, String statusMsg) {
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

