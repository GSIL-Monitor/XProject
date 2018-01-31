package com.xinguang.mq.configuration;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * ClassName: ConnectionManage 连接管理 <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年3月3日 上午11:41:28 <br/>
 *
 * @author lengxing
 * @version 
 * @since JDK 1.7
 */

public class ActiveConnection {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveConnection.class);
	
	private Connection connection;
	
	public ActiveConnection(PooledConnectionFactory pooledConnectionFactory){
		
		try {
			PooledConnection pooledConnection = (PooledConnection) pooledConnectionFactory.createConnection();
			// 必须start，否则无法接收消息  
			pooledConnection.start();
			
			//创建连接
			connection = pooledConnection.getConnection();
			
		} catch (JMSException e) {
			e.printStackTrace();
		}  
	}
	
	
	public Session createChannel(boolean autoAck) {
		if(connection == null){
			LOGGER.error("activemq连接不存在，请检查！");
		}
		Session session = null;
		try {
			if(autoAck){
				session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			}else{
				session = connection.createSession(Boolean.FALSE, ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}
	
	public void shutdown() throws IOException {
		if (connection != null)
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
	}
	
	
}
