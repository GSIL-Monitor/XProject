package com.xinguang.xherald.mq.utils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xinguang.xherald.mq.beans.MQConnection;
import com.xinguang.xherald.mq.beans.MQInitConnection;
import com.xinguang.xherald.mq.constant.MQES;
import com.xinguang.xherald.web.request.TopologyRequest;

/**
 * 
 * ClassName: MQUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年9月23日 下午3:28:22 <br/>
 *
 * @author Administrator-lengxing
 * @version
 * @since JDK 1.7
 */
@Component
public class MQConnectionUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(MQConnectionUtil.class);

	@Autowired
	private MQInitConnection mqInitConnection;

	// 创建连接
	public static ConnectionFactory getConnectionFactory(MQConnection mqConnection) {
		ConnectionFactory connFactory = new ConnectionFactory();

		// 设置基本信息
		connFactory.setHost(mqConnection.getHost());
		connFactory.setPort(mqConnection.getPort());
		connFactory.setVirtualHost(mqConnection.getVhost());
		connFactory.setUsername(mqConnection.getUsername());
		connFactory.setPassword(mqConnection.getPassword());

		// 加上断开重试机制：
		connFactory.setAutomaticRecoveryEnabled(true);
		connFactory.setNetworkRecoveryInterval(10000);

		return connFactory;
	}

	// 创建连接
	public static CachingConnectionFactory getCachingConnectionFactory(Map<String, String> configMap) {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setHost(configMap.get(MQES.HOST));
		cachingConnectionFactory.setPort(Integer.parseInt(configMap.get(MQES.PORT)));
		cachingConnectionFactory.setVirtualHost(configMap.get(MQES.VHOST));
		cachingConnectionFactory.setUsername(configMap.get(MQES.USERNAME));
		cachingConnectionFactory.setPassword(configMap.get(MQES.PASSWORD).trim());//
		return cachingConnectionFactory;
	}

	// 关闭连接
	public static void closeMQConnection(Connection connection, Channel channel, TopologyRequest request) {
		try {
			// 先关闭channel
			if (channel != null && channel.isOpen()) {
				channel.close();
			}

			// 再关闭connection
			if (connection != null && connection.isOpen()) {
				connection.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(
					" close the connecton of rabbitmq cluster failed. function={}, exchange={}, queueName={}, routingKey={}, errorMessage={}",
					"createMQTopology", request.getExchangeName(), request.getQueueName(), request.getQueueName(), e);
		}
	}

	public MQConnection setMQConnection(TopologyRequest request) {
		MQConnection mqConnection = new MQConnection();
		mqConnection.setHost(mqInitConnection.getHost());
		mqConnection.setPort(mqInitConnection.getPort());
		mqConnection.setVhost(request.getVhost());
		mqConnection.setUsername(request.getUserName());
		mqConnection.setPassword(DecryptMethod.getDecryptValue(request.getPassword().trim()));//解密密码
		return mqConnection;
	}

	/**
	 * createMQConnection:创建Connection. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator-lengxing
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws TimeoutException
	 * @since JDK 1.7
	 */
	public Connection createMQConnection(TopologyRequest request) throws IOException, TimeoutException {
		MQConnection mqConnection = setMQConnection(request);
		ConnectionFactory connFactory = MQConnectionUtil.getConnectionFactory(mqConnection);
		return connFactory.newConnection();
	}

	/**
	 * createMQChannel:创建Channel. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator-lengxing
	 * @param connection
	 * @return
	 * @throws IOException
	 * @since JDK 1.7
	 */
	public Channel createMQChannel(Connection connection) throws IOException {
		return connection.createChannel();
	}
	
	public String getMQHost(){
		return mqInitConnection.getHost();
	}
	
	public int getMQPort(){
		return mqInitConnection.getPort();
	}

}
