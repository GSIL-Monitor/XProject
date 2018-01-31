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
 * ClassName: MQCheckHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年10月12日 下午12:01:04 <br/>
 *
 * @author Administrator-lengxing
 * @version 
 * @since JDK 1.7
 */
@Component
public class MQCheckHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(MQCheckHandler.class);
	
	@Autowired
	private MQConnectionUtil mqUtil;

	public StatusEnum checkIsExsit(TopologyRequest request, Connection connection, Channel channel) throws Exception{
		boolean exchange_exist = true;
		boolean queue_exist = true;

		// 步骤一：判定exchange和queue是否存在
		try {
			channel.exchangeDeclarePassive(request.getExchangeName());
			return StatusEnum.MQStatus_002;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			exchange_exist = false;
		}

		try {
			if(!channel.isOpen()){//创建Channel，如有异常，将抛出
				channel = connection.createChannel();
			}
			channel.queueDeclarePassive(request.getQueueName());
			return StatusEnum.MQStatus_003;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			queue_exist = false;
		}

		if (exchange_exist || queue_exist) {
			if (exchange_exist) {
				return StatusEnum.MQStatus_002;
			}
			if (queue_exist) {
				return StatusEnum.MQStatus_003;
			}
		}

		return StatusEnum.MQStatus_007;
	}
	
	/**
	 * checkMQTopology:检查mq(exchangge和queue是否存在). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator-lengxing
	 * @param request
	 * @return
	 * @since JDK 1.7
	 */
	public StatusEnum checkMQTopology(TopologyRequest request) {
		Connection connection;
		Channel channel;
		try {
			connection = mqUtil.createMQConnection(request);
			channel = mqUtil.createMQChannel(connection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(" connect rabbitmq cluster failed. function={}, request={}, errorMessage={}", "checkMQTopology",
					request, e);
			return StatusEnum.MQStatus_001;
			
		}
		StatusEnum statusEnum = null;
		try {
			statusEnum = checkIsExsit(request,connection,channel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(" connect rabbitmq cluster failed. function={}, request={}, errorMessage={}", "checkMQTopology",
					request, e);
			statusEnum = StatusEnum.MQStatus_001;
		}finally {
			//关闭连接
			MQConnectionUtil.closeMQConnection(connection, channel, request);
		}
		return statusEnum;
	}

}
