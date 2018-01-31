package com.xinguang.mq.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.xinguang.mq.beans.MQMessage;
import com.xinguang.mq.beans.RabbitDelayQueueConnectionBean;
import com.xinguang.mq.beans.RabbitMessage;
import com.xinguang.mq.configuration.RabbitConnection;
import com.xinguang.mq.service.ProducerService;
import com.xinguang.mq.util.XStringUtils;

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
public class DelayQueueProducer implements InitializingBean,DisposableBean,ProducerService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DelayQueueProducer.class);
	
	private final static int delayQueueParamNum = 12;
	
	private String rmqServerIP;//ip地址
	private int rmqServerPort;//端口	
	private String paramStr;

	private Map<String, Channel> channelMap;
	private List<RabbitDelayQueueConnectionBean> rcbList = new ArrayList<RabbitDelayQueueConnectionBean>();
	
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
		//创建连接管理器(申明及绑定)		
		rcbList = getRCBList(paramStr);
		if(rcbList == null || rcbList.size() == 0){
			return;
		}
		channelMap = new RabbitConnection().getDelayQueueConnMap(rmqServerIP, rmqServerPort,rcbList);

		for (RabbitDelayQueueConnectionBean rcb : rcbList) {
			if (channelMap.get(rcb.getExchange()) == null) {
				LOGGER.error("delayqueue生产端初始化exchange:" + rcb.getExchange()+ "相对应的channel创建失败，请检查！");
				continue;
			} else {
				Channel channel = channelMap.get(rcb.getExchange());
				boolean durable = true;// 是否持久化
				boolean autoDelete = false;// 是否自动删除
				
				channel.exchangeDeclare(rcb.getExchange(), rcb.getExchangeType(), durable, autoDelete, null);
				channel.exchangeDeclare(rcb.getPostExchange(), rcb.getPostExchangeType(), durable, autoDelete, null);

				HashMap<String, Object> argumentsDelay = new HashMap<String, Object>();
				argumentsDelay.put("x-dead-letter-exchange", rcb.getPostExchange());
				argumentsDelay.put("x-message-ttl", rcb.getPerDelayQueueMessageTTL());
				channel.queueDeclare(rcb.getDelayQueue(), true, false, false, argumentsDelay);
				channel.queueBind(rcb.getDelayQueue(), rcb.getExchange(), rcb.getDelayQueue());
				
				channel.queueDeclare(rcb.getPostQueue(), true, false, false, null);
				channel.queueBind(rcb.getPostQueue(), rcb.getPostExchange(), rcb.getPostRoutingKey());
			}
		}
		
	}
	
	@Override
	public <T extends MQMessage> void sendMessage(T msg){
		RabbitMessage rmsg = (RabbitMessage) msg;
		try {
			Channel channel = null;
			if(channelMap == null || channelMap.get(rmsg.getExchange()) == null){
				//System.out.println("channel 未创建，请检查！");
				LOGGER.error("delayqueue生产端 channel 未创建，请检查！");
			}else{
				channel = channelMap.get(rmsg.getExchange());
			}
			channel.basicPublish(rmsg.getExchange(), rmsg.getRoutingKey(),MessageProperties.PERSISTENT_TEXT_PLAIN, rmsg.getMsgBody().toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Override
	public void destroy() throws Exception{
	}
	
	private List<RabbitDelayQueueConnectionBean> getRCBList(String paramStr) {
		String[] prdList = XStringUtils.splitCommon(paramStr,
				XStringUtils.COMMAS);
		if (prdList == null || prdList.length == 0) {
			return null;
		}

		for (String prd : prdList) {
			String[] paramList = XStringUtils.splitCommon(prd,
					XStringUtils.VERTICALBAR);
			if (paramList == null || paramList.length != delayQueueParamNum) {
				return null;
			}
			RabbitDelayQueueConnectionBean rcb = new RabbitDelayQueueConnectionBean();
			rcb.setUserName(paramList[0]);
			rcb.setPassword(paramList[1]);
			rcb.setVirtualHost(paramList[2]);
			rcb.setExchange(paramList[3]);
			rcb.setExchangeType(paramList[4]);
			rcb.setDelayQueue(paramList[5]);
			rcb.setDelayRoutingKey(paramList[6]);
			rcb.setPostExchange(paramList[7]);
			rcb.setPostExchangeType(paramList[8]);
			rcb.setPostQueue(paramList[9]);
			rcb.setPostRoutingKey(paramList[10]);
			rcb.setPerDelayQueueMessageTTL(Integer.valueOf(paramList[11]));
			rcbList.add(rcb);
		}
		return rcbList;
	}

}