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
public class MQPrdDestination {
	
	// rabbitmq
	private String exchange;// 交换器
	private String routingKey;// 路由key

	// activemq
	private String destName;
	private int destType;

	public MQPrdDestination(String exchange, String routingKey) {
		super();
		this.exchange = exchange;
		this.routingKey = routingKey;
	}

	public MQPrdDestination(int destType, String destName) {
		super();
		this.destName = destName;
		this.destType = destType;
	}

	public String getExchange() {
		return exchange;
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public String getDestName() {
		return destName;
	}

	public int getDestType() {
		return destType;
	}

}

