/**
 * Project Name:mq
 * File Name:ActiveBase.java
 * Package Name:com.xinguang.mq.service
 * Date:2017年3月1日上午11:39:42
 * Copyright (c) 2017, lengxing@xinguangnet.com All Rights Reserved.
 *
*/

package com.xinguang.mq.service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinguang.mq.enums.ActiveDestEnum;

/**
 * ClassName:ActiveBase <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年3月1日 上午11:39:42 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class ActiveMQBase {
	
	protected Session session;
	
	protected String isopen;

	public String getIsopen() {
		return isopen;
	}

	public void setIsopen(String isopen) {
		this.isopen = isopen;
	}

	@Autowired
	protected PooledConnectionFactory pooledConnectionFactory;
	
	public PooledConnectionFactory getPooledConnectionFactory() {
		return pooledConnectionFactory;
	}

	public void setPooledConnectionFactory(
			PooledConnectionFactory pooledConnectionFactory) {
		this.pooledConnectionFactory = pooledConnectionFactory;
	}
	
	
	
	protected Destination getDestination(int destType, String destName) throws JMSException{	
		if(ActiveDestEnum.QUEUE.getIndex() == destType){ //队列消息  
            return session.createQueue(destName);  
        }else if(ActiveDestEnum.TOPIC.getIndex() == destType){ //主题消息  
            return session.createTopic(destName);  
        }else{  
            throw new RuntimeException("DestinationType非法，不是队列消息或主题消息");  
        }
	}
}

