/**
 * Project Name:mqclient
 * File Name:MQClient.java
 * Package Name:com.xinguang.mq.client
 * Date:2017年3月2日下午7:23:48
 * Copyright (c) 2017, lengxing@xinguangnet.com All Rights Reserved.
 *
 */

package com.xinguang.mq.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinguang.mq.beans.MQCosDestination;
import com.xinguang.mq.enums.MQEnum;
import com.xinguang.mq.service.ConsumerMsgCallBack;
import com.xinguang.mq.serviceImpl.ActiveConsumer;
import com.xinguang.mq.serviceImpl.RabbitConsumer;

/**
 * 
 * ClassName: MQConsumerClient <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年3月3日 下午2:52:26 <br/>
 *
 * @author lengxing
 * @version
 * @since JDK 1.7
 */

@Component
public class MQConsumerClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(MQConsumerClient.class);

	private int mqType;

	public int getMqType() {
		return mqType;
	}

	public void setMqType(int mqType) {
		this.mqType = mqType;
	}

	@Autowired
	private RabbitConsumer rabbitConsumer;
	
	@Autowired
	private ActiveConsumer activeConsumer;

	public void receiveMsg(MQCosDestination dest,ConsumerMsgCallBack callback) throws Exception {
		if (mqType == MQEnum.RABBITMQ.getIndex()) {
			rabbitConsumer.receiveMsg(dest.getQueueName(),callback);
		} else if ((mqType == MQEnum.ACTIVEMQ.getIndex())) {
			activeConsumer.receiveMsg(dest.getDestType(), dest.getDestName(),callback);
		} else {
			LOGGER.error("消费端mq类型和相应的参数不匹配！");
		}
	}

}
