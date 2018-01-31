package com.xinguang.mq.serviceImpl;

import java.io.Serializable;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.xinguang.mq.beans.ActiveMessage;
import com.xinguang.mq.beans.MQMessage;
import com.xinguang.mq.configuration.ActiveConnection;
import com.xinguang.mq.service.ActiveMQBase;
import com.xinguang.mq.service.ProducerService;

/**
 * 
 * ClassName: RmqProducer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年3月3日 上午10:21:41 <br/>
 *
 * @author lengxing
 * @version
 * @since JDK 1.7
 */
@Service
public class ActiveProducer extends ActiveMQBase implements InitializingBean,DisposableBean,ProducerService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveProducer.class);
	
	private ActiveConnection activeConnection;

	/**
	 * 初始化
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		//创建连接
		if (!isopen.equals("true")) {
			return;
		}
		activeConnection = new ActiveConnection(pooledConnectionFactory);
		if(activeConnection == null){
			return;
		}
		session = activeConnection.createChannel(true);
	}
	
	@Override
	public <T extends MQMessage> void sendMessage(T msg){
		if(session == null){
			//LOGGER.error("activemq生产端 session 未创建，请检查！");
			throw new RuntimeException("activemq生产端 session 未创建，请检查！"); 
		}
		ActiveMessage amsg = (ActiveMessage) msg;
		try {
			MessageProducer msgProducer = session.createProducer(getDestination(amsg.getDestType(),amsg.getDestName()));
			msgProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
			ObjectMessage message = session.createObjectMessage();
			message.setObject((Serializable)amsg.getMsgBody());
			LOGGER.info("activemq生产端:" + msg.getMsgBody());
			msgProducer.send(message);
			//session.commit();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() throws Exception {
		activeConnection.shutdown();
	}
}