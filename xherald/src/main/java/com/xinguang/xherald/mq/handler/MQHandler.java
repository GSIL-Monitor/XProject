package com.xinguang.xherald.mq.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xinguang.xherald.mq.utils.MQConnectionUtil;
import com.xinguang.xherald.web.enums.StatusEnum;
import com.xinguang.xherald.web.request.TopologyRequest;

/**
 * ClassName:MQHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年7月13日 下午7:53:21 <br/>
 * 
 * @author hzlengxing
 * @version
 * @since JDK 1.7
 * @see
 */
@Component
public class MQHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(MQHandler.class);

	private static final String policyType = "topic";
	
	@Autowired
	private MQConnectionUtil mqUtil;

	/**
	 * createMQTopology:创建MQTopology(exchange和queue). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator-lengxing
	 * @param request
	 * @return
	 * @throws Exception
	 * @since JDK 1.7
	 */
	public StatusEnum createMQTopology(TopologyRequest request) throws Exception {
		// 步骤一：创建连接和通道		
		Connection connection;
		Channel channel;
		try {
			connection = mqUtil.createMQConnection(request);
			channel = mqUtil.createMQChannel(connection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(" connect rabbitmq cluster failed. function={}, request={}, errorMessage={}", "setConnFactory",
					request, e);
			return StatusEnum.MQStatus_001;
		}
		
		//步骤二：创建mq拓扑结果
		return createTopology(request,connection,channel);
	}

	/**
	 * createTopology:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator-lengxing
	 * @param request
	 * @param connection
	 * @param channel
	 * @return
	 * @since JDK 1.7
	 */
	public StatusEnum createTopology(TopologyRequest request, Connection connection, Channel channel) {
		//创建mq拓扑结构
		try {
			channel.exchangeDeclare(request.getExchangeName(), policyType, true);
			channel.queueDeclare(request.getQueueName(), true, false, false, null);
			channel.queueBind(request.getQueueName(), request.getExchangeName(), request.getRoutingKey());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(
					" create MQTopology failed. function={}, exchange={}, queueName={}, routingKey={}, errorMessage={}",
					"createMQTopology", request.getExchangeName(), request.getQueueName(), request.getQueueName(), e);
			return StatusEnum.MQStatus_004;
		} finally {
			MQConnectionUtil.closeMQConnection(connection, channel, request);
		}
		
		return StatusEnum.MQStatus_005;
	}

}
