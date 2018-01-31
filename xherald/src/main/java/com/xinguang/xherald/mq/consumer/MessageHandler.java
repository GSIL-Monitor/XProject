package com.xinguang.xherald.mq.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.context.ApplicationContextAware;

public interface MessageHandler extends ChannelAwareMessageListener,ApplicationContextAware{
	public static final String DEFAULT_CHARSET = "UTF-8";
	
	/**
	 * 校验消息格式
	 * analyticMessage:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator-lengxing
	 * @param message
	 * @return
	 * @since JDK 1.7
	 */
	boolean analyticMessage(Message message);
}