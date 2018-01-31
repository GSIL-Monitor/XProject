package com.xinguang.xherald.mq.thread;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import com.xinguang.xherald.mq.consumer.MQConsumer;
public class Task implements Runnable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Task.class);

	private Map<String, String> configMap;
	
	public Task setConfig(Map<String, String> configMap){
		this.configMap = configMap;
		return this;
	}

	@Override
	public void run() {
		try {
			//监听相应的queue，将监听的消息推送至具体的消费者（如es）
			MQConsumer mqConsumer = new MQConsumer();
			SimpleMessageListenerContainer simpelListener=mqConsumer.receive(configMap);
			//System.out.println("simpelListener:"+simpelListener.isActive());
			LOGGER.info("configMap={}, simpelListener={}", configMap, simpelListener.isActive());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error("Linstener run error. function={}, configMap={}, errorMessage={}", "run", configMap, e);			
		}
	}
}