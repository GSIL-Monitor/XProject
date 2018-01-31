package com.xinguang.mq.serviceImpl;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.xinguang.mq.configuration.ActiveConnection;
import com.xinguang.mq.service.ActiveMQBase;
import com.xinguang.mq.service.ConsumerMsgCallBack;

/**
 * 
 * ClassName: ActiveConsumer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年3月6日 下午1:39:49 <br/>
 *
 * @author lengxing
 * @version
 * @since JDK 1.7
 */
public class ActiveConsumer extends ActiveMQBase implements InitializingBean,
		DisposableBean {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ActiveConsumer.class);

	private ActiveConnection activeConnection;

	/**
	 * 初始化
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// 创建连接
		if (!isopen.equals("true")) {
			return;
		}
		activeConnection = new ActiveConnection(pooledConnectionFactory);
		session = activeConnection.createChannel(false);
	}

	/**
	 * 接收信息
	 * 
	 * @param msg
	 */
	public void receiveMsg(int destType, String destName,
			final ConsumerMsgCallBack callback) throws Exception {
		if(session == null){
			//LOGGER.error("activemq消费端 session 未创建，请检查！");
			throw new RuntimeException("activemq消费端 session 未创建，请检查！");  
		}
		MessageConsumer msgConsumer = session.createConsumer(getDestination(
				destType, destName));
		msgConsumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message msg) {
				ObjectMessage objectMessage = (ObjectMessage) msg;
				String message = null;
				try {
					message = objectMessage.getObject().toString();

					boolean flag = callback.ackMsg(message);

					if (flag) {
						msg.acknowledge();
						LOGGER.info("activemq 消息：" + message + "消费成功！");
					} else {
						LOGGER.error("activemq 消息" + message + "消费成失败！");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("");
	}

}