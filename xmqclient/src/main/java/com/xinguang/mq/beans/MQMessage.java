/**
 * Project Name:mqclient
 * File Name:MQMessage.java
 * Package Name:com.xinguang.mq.beans
 * Date:2017年3月2日下午7:31:33
 * Copyright (c) 2017, lengxing@xinguangnet.com All Rights Reserved.
 *
 */

package com.xinguang.mq.beans;

/**
 * ClassName:MQMessage <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年3月2日 下午7:31:33 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
public class MQMessage {
	
	protected String msgBody;

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public MQMessage(String msgBody) {
		super();
		this.msgBody = msgBody;
	}
	
	

}
