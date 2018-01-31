/**
 * Project Name:mqclient
 * File Name:MQDestination.java
 * Package Name:com.xinguang.mq.beans
 * Date:2017年3月3日下午3:48:44
 * Copyright (c) 2017, lengxing@xinguangnet.com All Rights Reserved.
 *
*/

package com.xinguang.mq.beans;

/**
 * ClassName:MQDestination <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年3月3日 下午3:48:44 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
public class MQCosDestination {
	
	// rabbitmq
	private String QueueName;

	// activemq
	private String destName;
	private int destType;

	public MQCosDestination(int destType, String destName) {
		super();
		this.destName = destName;
		this.destType = destType;
	}

	public MQCosDestination(String queueName) {
		super();
		QueueName = queueName;
	}

	public String getQueueName() {
		return QueueName;
	}

	public String getDestName() {
		return destName;
	}

	public int getDestType() {
		return destType;
	}

}

