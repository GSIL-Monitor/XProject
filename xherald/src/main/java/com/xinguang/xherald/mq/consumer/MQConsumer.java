package com.xinguang.xherald.mq.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import com.xinguang.xherald.mq.constant.MQES;
import com.xinguang.xherald.mq.consumer.impl.ESMessageHandler;
import com.xinguang.xherald.mq.utils.MQConnectionUtil;

public class MQConsumer {

	public static Map<Map<String, String>, List<SimpleMessageListenerContainer>> msgListenerMap = new ConcurrentHashMap<Map<String, String>, List<SimpleMessageListenerContainer>>();

	public SimpleMessageListenerContainer receive(Map<String, String> configMap) throws Exception{
		SimpleMessageListenerContainer container = messageContainer(configMap);
		container.setMessageListener(new MessageListenerAdapter(new ESMessageHandler(configMap)));
		if (msgListenerMap.containsKey(configMap)) {
			List<SimpleMessageListenerContainer> listenerList = msgListenerMap.get(configMap);
			listenerList.add(container);
			msgListenerMap.put(configMap, listenerList);
		} else {
			List<SimpleMessageListenerContainer> listenerList = new ArrayList<SimpleMessageListenerContainer>();
			listenerList.add(container);
			msgListenerMap.put(configMap, listenerList);
		}
		container.start();
		return container;
	}
	
	public static synchronized Map<Map<String, String>, List<SimpleMessageListenerContainer>> getMsgMapInstance() {
		if (msgListenerMap == null) {
			msgListenerMap = new ConcurrentHashMap<Map<String, String>, List<SimpleMessageListenerContainer>>();
		}
		return msgListenerMap;
	}

	private SimpleMessageListenerContainer messageContainer(Map<String, String> configMap) throws Exception{
		ConnectionFactory connectionFactory = MQConnectionUtil.getCachingConnectionFactory(configMap);
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(new Queue(configMap.get(MQES.QUEUENAME), true));
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(1);
		container.setConcurrentConsumers(1);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
		return container;
	}

}