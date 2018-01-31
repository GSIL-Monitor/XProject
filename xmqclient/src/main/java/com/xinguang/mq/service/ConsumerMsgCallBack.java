/**
 * Project Name:mqclient
 * File Name:ConsumerMsgCallBack.java
 * Package Name:com.xinguang.mq.service
 * Date:2017年3月8日上午11:02:12
 * Copyright (c) 2017, lengxing@xinguangnet.com All Rights Reserved.
 *
*/

package com.xinguang.mq.service;
/**
 * ClassName:ConsumerMsgCallBack <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年3月8日 上午11:02:12 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public interface ConsumerMsgCallBack {
	/**
	 * 确认消息是否被消费
	 */
	 boolean ackMsg(String msg);
}

