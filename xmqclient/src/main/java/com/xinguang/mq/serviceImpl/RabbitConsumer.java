package com.xinguang.mq.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.xinguang.mq.beans.RabbitCosConnectionBean;
import com.xinguang.mq.configuration.RabbitConnection;
import com.xinguang.mq.service.ConsumerMsgCallBack;
import com.xinguang.mq.util.XStringUtils;

/**
 * 
 * ClassName: RabbitConsumer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年3月3日 下午4:42:10 <br/>
 *
 * @author lengxing
 * @version 
 * @since JDK 1.7
 */
public class RabbitConsumer implements InitializingBean,DisposableBean{
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitProducer.class);
	
	private final static int consumerParamNum = 4;
	
	private String rmqServerIP;//ip地址
	private int rmqServerPort;//端口
	private String paramStr;
	
	private Map<String, Channel> channelMap;
	private List<RabbitCosConnectionBean> rcbList = new ArrayList<RabbitCosConnectionBean>();
	
	

	public String getRmqServerIP() {
		return rmqServerIP;
	}

	public int getRmqServerPort() {
		return rmqServerPort;
	}

	public String getParamStr() {
		return paramStr;
	}

	public void setRmqServerIP(String rmqServerIP) {
		this.rmqServerIP = rmqServerIP;
	}

	public void setRmqServerPort(int rmqServerPort) {
		this.rmqServerPort = rmqServerPort;
	}

	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}

	/**
	 * 初始化
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		//创建连接管理器		
		rcbList = getRCBList(paramStr);
		if(rcbList == null || rcbList.size() == 0){
			return;
		}
		channelMap = new RabbitConnection().getCosConnMap(rmqServerIP, rmqServerPort,rcbList);

		for (RabbitCosConnectionBean rcb : rcbList) {
			if (channelMap.get(rcb.getQueue()) == null) {
				System.out.println("mq消费端初始化queue:" + rcb.getQueue()+ "相对应的channel创建失败，请检查！");
				continue;
			}
		}
	}
	
	/**
	 * 接收信息
	 * @param msg
	 */
	public void receiveMsg(String queueName,final ConsumerMsgCallBack callback) throws Exception {
		if(channelMap == null || channelMap.get(queueName) == null ){
			LOGGER.error("rabbitmq消费端queue:" + queueName + "没有相对应的channel，请检查！");
			return;
		}
		final Channel channel = channelMap.get(queueName);
		
		channel.queueDeclare(queueName, true, false, false, null);
		boolean autoAck = false;//手动提交
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				
				boolean flag = callback.ackMsg(message);
				
				if(flag){
					channel.basicAck(envelope.getDeliveryTag(), false);
					LOGGER.info("rabbitmq 消息：" + message + "消费成功！");
				}else{
					channel.basicNack(envelope.getDeliveryTag(), false, true);
					LOGGER.error("rabbitmq 消息" + message+ "消费成失败！");
				}
			}
		};
		
		channel.basicConsume(queueName, autoAck, consumer);
	}
	
	private List<RabbitCosConnectionBean> getRCBList(String paramStr) {
		String[] prdList = XStringUtils.splitCommon(paramStr,
				XStringUtils.COMMAS);
		if (prdList == null || prdList.length == 0) {
			return null;
		}

		for (String prd : prdList) {
			String[] paramList = XStringUtils.splitCommon(prd,
					XStringUtils.VERTICALBAR);
			if (paramList == null || paramList.length != consumerParamNum) {
				return null;
			}
			RabbitCosConnectionBean rcb = new RabbitCosConnectionBean();
			rcb.setUserName(paramList[0]);
			rcb.setPassword(paramList[1]);
			rcb.setVirtualHost(paramList[2]);
			rcb.setQueue(paramList[3]);
			rcbList.add(rcb);
		}
		return rcbList;
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Override
	public void destroy() throws Exception{
		
	}

}