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

import com.xinguang.mq.beans.ActiveMessage;
import com.xinguang.mq.beans.MQPrdDestination;
import com.xinguang.mq.beans.MQMessage;
import com.xinguang.mq.beans.RabbitMessage;
import com.xinguang.mq.enums.MQEnum;
import com.xinguang.mq.serviceImpl.ActiveProducer;
import com.xinguang.mq.serviceImpl.DelayQueueProducer;
import com.xinguang.mq.serviceImpl.RabbitProducer;

/**
 * 
 * ClassName: MQProducerClient <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年3月3日 下午4:36:18 <br/>
 *
 * @author lengxing
 * @version 
 * @since JDK 1.7
 */

@Component
public class MQProducerClient{
	private static final Logger LOGGER = LoggerFactory.getLogger(MQProducerClient.class);

	private int mqType;

	public int getMqType() {
		return mqType;
	}

	public void setMqType(int mqType) {
		this.mqType = mqType;
	}

	@Autowired
	private RabbitProducer rabbitProducer;
	
	@Autowired
	private ActiveProducer activeProducer;
	
	@Autowired
	private DelayQueueProducer delayProducer;

	public void send(MQPrdDestination dest, MQMessage mqMsg) {
		if (mqType == MQEnum.RABBITMQ.getIndex()) {
			RabbitMessage rm = new RabbitMessage(dest.getExchange(),dest.getRoutingKey(), mqMsg.getMsgBody());
			rabbitProducer.sendMessage(rm);
		} else if (mqType == MQEnum.ACTIVEMQ.getIndex()) {
			ActiveMessage am = new ActiveMessage(dest.getDestName(), dest.getDestType(), mqMsg.getMsgBody());
			activeProducer.sendMessage(am);
		} else if (mqType == MQEnum.DELAYQUEUE.getIndex()) {
			RabbitMessage am = new RabbitMessage(dest.getExchange(),dest.getRoutingKey(),mqMsg.getMsgBody());
			delayProducer.sendMessage(am);
		} else {
			LOGGER.error("生产端mq类型和相应的参数不匹配！");
		}
	}

}
