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
 * ClassName: RabbitCosConnectionBean <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年3月9日 下午2:52:26 <br/>
 *
 * @author lengxing
 * @version 
 * @since JDK 1.7
 */
public class RabbitCosConnectionBean {
	private String userName;
	private String password;
	private String virtualHost;
	private String queue;
	
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public String getVirtualHost() {
		return virtualHost;
	}
	public String getQueue() {
		return queue;
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
	public void setQueue(String queue) {
		this.queue = queue;
	}
	
}
