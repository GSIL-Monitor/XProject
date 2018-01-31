package com.xinguang.mq.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xinguang.mq.beans.RabbitCosConnectionBean;
import com.xinguang.mq.beans.RabbitDelayQueueConnectionBean;
import com.xinguang.mq.beans.RabbitPrdConnectionBean;

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
public class RabbitConnection {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitConnection.class);
	
	private Map<String, Connection> prdConnMap;
	
	private Map<String, Connection> cosConnMap;
	
	private Map<String, Connection> delayQueueConnMap;
	

	public Map<String, Channel> getPrdConnMap(String rmqServerIP, int rmqServerPort, List<RabbitPrdConnectionBean> paramList){
		prdConnMap = new HashMap<String, Connection>();
		Map<String, Channel> channelMap = new HashMap<String, Channel>();
		for(RabbitPrdConnectionBean rcb : paramList){
			
			Connection conn = getConnection(rmqServerIP,rmqServerPort,rcb.getUserName(),rcb.getPassword(),rcb.getVirtualHost());
			if(conn != null){
				Channel channel = createChannel(conn);
				if(channel != null){
					prdConnMap.put(rcb.getExchange(), conn);
					channelMap.put(rcb.getExchange(), channel);
				}else{
					try {
						shutdown(conn);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return channelMap;
	}
	
	public Map<String, Channel> getCosConnMap(String rmqServerIP, int rmqServerPort, List<RabbitCosConnectionBean> paramList){
		cosConnMap = new HashMap<String, Connection>();
		Map<String, Channel> channelMap = new HashMap<String, Channel>();
		for(RabbitCosConnectionBean rcb : paramList){
			
			Connection conn = getConnection(rmqServerIP,rmqServerPort,rcb.getUserName(),rcb.getPassword(),rcb.getVirtualHost());
			if(conn != null){
				Channel channel = createChannel(conn);
				if(channel != null){
					cosConnMap.put(rcb.getQueue(), conn);
					channelMap.put(rcb.getQueue(), channel);
				}else{
					try {
						shutdown(conn);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return channelMap;
	}
	
	public Map<String, Channel> getDelayQueueConnMap(String rmqServerIP, int rmqServerPort, List<RabbitDelayQueueConnectionBean> paramList){
		delayQueueConnMap = new HashMap<String, Connection>();
		Map<String, Channel> channelMap = new HashMap<String, Channel>();
		for(RabbitDelayQueueConnectionBean rcb : paramList){
			
			Connection conn = getConnection(rmqServerIP,rmqServerPort,rcb.getUserName(),rcb.getPassword(),rcb.getVirtualHost());
			if(conn != null){
				Channel channel = createChannel(conn);
				if(channel != null){
					delayQueueConnMap.put(rcb.getExchange(), conn);
					channelMap.put(rcb.getExchange(), channel);
				}else{
					try {
						shutdown(conn);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return channelMap;
	}

	public Channel createChannel(Connection conn) {
		Channel channel = null;
		try {
			channel = conn.createChannel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channel;
	}

	public void shutdown(Connection conn) throws IOException {
		if (conn != null)
			conn.close();
	}
	
	private Connection getConnection(String rmqServerIP, int rmqServerPort, String userName, String password, String virtualHost){
		ConnectionFactory connFactory = new ConnectionFactory();
		connFactory.setHost(rmqServerIP);
		connFactory.setPort(rmqServerPort);
		connFactory.setUsername(userName);
		connFactory.setPassword(password);
		connFactory.setVirtualHost(virtualHost);
		
		// 加上断开重试机制：
		connFactory.setAutomaticRecoveryEnabled(true);
		connFactory.setNetworkRecoveryInterval(10000);
		
		try {
			return connFactory.newConnection();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
}
