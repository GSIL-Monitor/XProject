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
 * date: 2017年3月9日 下午2:51:25 <br/>
 *
 * @author lengxing
 * @version 
 * @since JDK 1.7
 */
public class RabbitDelayQueueConnectionBean {
	private String userName;
	private String password;
	private String virtualHost;
	
	private String exchange;
	private String exchangeType;
	private String delayQueue;
	private String delayRoutingKey;
	private String postExchange;
	private String postExchangeType;
	private String postQueue;
	private String postRoutingKey;
	private long perDelayQueueMessageTTL;

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
	public String getDelayQueue() {
		return delayQueue;
	}
	public String getDelayRoutingKey() {
		return delayRoutingKey;
	}
	public String getPostExchange() {
		return postExchange;
	}
	public String getPostExchangeType() {
		return postExchangeType;
	}
	public String getPostQueue() {
		return postQueue;
	}
	public String getPostRoutingKey() {
		return postRoutingKey;
	}
	public void setDelayQueue(String delayQueue) {
		this.delayQueue = delayQueue;
	}
	public void setDelayRoutingKey(String delayRoutingKey) {
		this.delayRoutingKey = delayRoutingKey;
	}
	public void setPostExchange(String postExchange) {
		this.postExchange = postExchange;
	}
	public void setPostExchangeType(String postExchangeType) {
		this.postExchangeType = postExchangeType;
	}
	public void setPostQueue(String postQueue) {
		this.postQueue = postQueue;
	}
	public void setPostRoutingKey(String postRoutingKey) {
		this.postRoutingKey = postRoutingKey;
	}
	public long getPerDelayQueueMessageTTL() {
		return perDelayQueueMessageTTL;
	}
	public void setPerDelayQueueMessageTTL(long perDelayQueueMessageTTL) {
		this.perDelayQueueMessageTTL = perDelayQueueMessageTTL;
	}

}
