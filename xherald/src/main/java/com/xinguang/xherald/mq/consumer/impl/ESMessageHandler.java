package com.xinguang.xherald.mq.consumer.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.common.base.Charsets;
import com.rabbitmq.client.Channel;
import com.xinguang.xherald.common.utils.JsonPojoUtil;
import com.xinguang.xherald.es.ESService;
import com.xinguang.xherald.mq.beans.MesPojo;
import com.xinguang.xherald.mq.constant.MQES;
import com.xinguang.xherald.mq.consumer.MessageHandler;
import com.xinguang.xherald.mq.enums.MQActionEnum;
import com.xinguang.xherald.mq.enums.MessageExpEnum;

@Component
public class ESMessageHandler implements MessageHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ESMessageHandler.class);

	private ESService esService;

	protected static ApplicationContext context;

	private String indexName;

	private String indexType;

	public ESMessageHandler(Map<String, String> configMap) {
		super();
		this.indexName = configMap.get(MQES.INDEXNAME);
		this.indexType = configMap.get(MQES.INDEXTYPE);
		this.esService = (ESService) context.getBean(ESService.class);// 注入bean
		this.setEsService(esService);
	}

	public ESMessageHandler() {
		super();
	}

	public ESService getEsService() {
		return esService;
	}

	public void setEsService(ESService esService) {
		this.esService = esService;
	}

	@Override
	public void onMessage(Message message,Channel channel) /* throws Exception */ {
		System.out.println("message:" + new String(message.getBody(),Charsets.UTF_8));
		MQActionEnum action = MQActionEnum.RETRY;

		try {
			// 接收消息并解析消息
			if (analyticMessage(message)) {
				MesPojo mesPojo = JsonPojoUtil.json2Obj(new String(message.getBody(),Charsets.UTF_8), MesPojo.class);

				Map<String, Object> data = null;
				try {
					data = JsonPojoUtil.obj2Map(mesPojo.getData());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new Exception(String.valueOf(MessageExpEnum.Mes_1.getStatusCode()), e);
				}

				boolean optFlag = false;
				
				String objid = mesPojo.getObjid();
				if(StringUtils.isBlank(objid)){
					LOGGER.info("The message body is illegal,the objid is null,message={}",mesPojo);
					throw new Exception(String.valueOf(MessageExpEnum.Mes_1.getStatusCode()));
				}
				
				if (MQES.ADD.equals(mesPojo.getObjopt().trim()) || MQES.UPDATE.equals(mesPojo.getObjopt().trim())) {
					// upsert(add/update)
					optFlag = esService.singleUpsert(indexName, indexType, mesPojo.getObjid(), data);
				} else if (MQES.DELETE.equals(mesPojo.getObjopt().trim())) {
					// delete
					optFlag = esService.deleteDoc(indexName, indexType, mesPojo.getObjid());
				} else {
					optFlag = true;// 无效数据直接过滤掉
				}

				if (optFlag) {
					action = MQActionEnum.ACCEPT;
				} else {// 消息落地不成功，重试
					action = MQActionEnum.RETRY;
				}
			} else {
				action = MQActionEnum.REJECT;
			}

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			// 根据异常种类决定是ACCEPT、RETRY还是 REJECT
			if (Integer.parseInt(ex.getMessage()) == MessageExpEnum.Mes_1.getStatusCode()) {// 如果消息格式和规定不一致，消息抛弃掉
				action = MQActionEnum.REJECT;
			} else {// 消息如果落地不成功，重试
				action = MQActionEnum.RETRY;
			}
			//LOGGER.error(" receive message for upsert index error. function={}, input={}, errorMessage={}", "onMessage",message, ex);
		} finally {
			// 通过finally块来保证Ack/Nack会且只会执行一次
			try {
				if (action == MQActionEnum.ACCEPT) {
					channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				} else if (action == MQActionEnum.RETRY) {
					/*
					 * arg0:deliveryTag:该消息的index
					 * arg1:multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
					 * arg2:requeue：被拒绝的是否重新入队列
					 */
					channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
				} else {
					channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOGGER.error(" mq Ack/Nack error. function={}, input={}, action={}, errorMessage={}", "onMessage",
						action, message, e);
			}
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	@Override
	public boolean analyticMessage(Message message) {
		// TODO Auto-generated method stub
		String mesJson = null;
		try {
			mesJson = new String(message.getBody(),Charsets.UTF_8);
			MesPojo mesPojo = JsonPojoUtil.json2Obj(mesJson, MesPojo.class);

			// 校验接收消息的必填项
			if (mesPojo != null && StringUtils.isNotBlank(mesPojo.getAppid())
					&& StringUtils.isNotBlank(mesPojo.getMsgtype()) && mesPojo.getCtime() > 0
					&& mesPojo.getData() != null) {
				/*if (MQES.OBJECT.equals(mesPojo.getMsgtype().trim()) && StringUtils.isBlank(mesPojo.getObjopt())) {
					return false;
				} else {
					return true;
				}*/
				return true;
			} else {
				LOGGER.info("The message body is illegal,message={}",mesJson);
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(" The message is not in conformity with the prescribed format."
					+ " function={}, input={}, errorMessage={}", "analyticMessage", mesJson, e);
			return false;
		}
	}

}