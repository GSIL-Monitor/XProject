package com.xinguang.xherald.mq.monitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinguang.xherald.mq.beans.MQInitConnection;
import com.xinguang.xherald.mq.consumer.MQConsumer;
import com.xinguang.xherald.mq.thread.ThreadManager;

/**
 * ClassName:MyExecutorService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年9月26日 下午8:10:56 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
@Component
public class MQConsumerMonitor {

	private static final Logger LOGGER = LoggerFactory.getLogger(MQConsumerMonitor.class);

	@Autowired
	private MQInitConnection mqInitConnection;

	// 创建线程池  
	public static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

	public void createExecutorService(final ThreadManager threadManager) {
		// 延迟5秒后，每隔60秒执行一次该任务
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				//测试
				//testCheck();
				
				// 具体执行过程
				healthCheck(threadManager);
			}
		}, 5, 60, TimeUnit.SECONDS);

	}

	private void healthCheck(ThreadManager threadManager) {
		int threadduplicated = mqInitConnection.getDuplicated();
		LOGGER.info("healthCheck... " + new Date());
		for (Map.Entry<Map<String, String>, List<SimpleMessageListenerContainer>> entry : MQConsumer.getMsgMapInstance()
				.entrySet()) {
			Map<String, String> msg = entry.getKey();
			List<SimpleMessageListenerContainer> containerList = entry.getValue();
			int num = 0;
			List<Integer> tobeRemoved = new ArrayList<Integer>();
			for (int i = 0; i < containerList.size(); i++) {
				if (containerList.get(i).isActive()) {
					num++;
				} else {
					containerList.get(i).stop();
					tobeRemoved.add(i);
				}
			}
			for (int i = tobeRemoved.size() - 1; i >= 0; i--) {
				containerList.remove(tobeRemoved.get(i));
			}
			if (num <= 1) {
				if (msg != null && !msg.isEmpty()) {
					threadManager.createThread(msg, threadduplicated - num);
				}
			}
		}
	}
	
	public void testCheck(){
		for (Map.Entry<Map<String, String>, List<SimpleMessageListenerContainer>> entry : MQConsumer.getMsgMapInstance()
				.entrySet()) {
			List<SimpleMessageListenerContainer> containerList = entry.getValue();
			for (int i = 0; i < containerList.size(); i++) {
				if (containerList.get(i).isActive()) {
					containerList.get(i).stop();
				} else {
					continue;
				}
			}			
		}
	}
}
