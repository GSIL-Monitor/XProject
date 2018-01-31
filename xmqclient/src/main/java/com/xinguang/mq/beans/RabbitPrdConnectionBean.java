/**
 * Project Name:mqclient
 * File Name:RabbitConnectionBean.java
 * Package Name:com.xinguang.mq.beans
 * Date:2017年3月9日上午9:48:51
 * Copyright (c) 2017, lengxing@xinguangnet.com All Rights Reserved.
 *
 */

package com.xinguang.mq.beans;

/**
 * 
 * ClassName: RabbitPrdConnectionBean <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年3月9日 下午3:18:39 <br/>
 *
 * @author lengxing
 * @version 
 * @since JDK 1.7
 */
public class RabbitPrdConnectionBean {
	private String userName;
	private String password;
	private String virtualHost;
	private String exchange;
	private String exchangeType;
	private String queue;
	private String routingKey;
	
	
	
	
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public String getVirtualHost() {
		return virtualHost;
	}
	public String getExchange() {
		return exchange;
	}
	public String getExchangeType() {
		return exchangeType;
	}
	public String getQueue() {
		return queue;
	}
	public String getRoutingKey() {
		return routingKey;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}
	
	
}
