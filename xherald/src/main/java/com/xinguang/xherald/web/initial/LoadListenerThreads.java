package com.xinguang.xherald.web.initial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.xinguang.xherald.mq.constant.MQES;
import com.xinguang.xherald.mq.monitor.MQConsumerMonitor;
import com.xinguang.xherald.mq.thread.ThreadManager;
import com.xinguang.xherald.web.file.ReadFile;

@Component
public class LoadListenerThreads implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoadListenerThreads.class);
	
	@Autowired
	private ThreadManager threadManager;
	
	@Autowired
	private ReadFile readFile;
	
	@Autowired
	private MQConsumerMonitor monitor;
	
	@Value("${filePath}")
    private String filePath;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		/*	root application context 没有parent（避免多次执行，可能同一个web项目有多个容器）
			ApplicationListener<ContextRefreshedEvent>使用时，会存在一个问题，在web 项目中（spring mvc），系统会存在两个容器，
			一个是root application context ,另一个就是我们自己的 projectName-servlet  context（作为root application context的子容器）。
			这种情况下，就会造成onApplicationEvent方法被执行两次。
		*/
		if (event.getApplicationContext().getParent() == null) {
			// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			LOGGER.info("-----Load Listener Threads Start---");
			String path = new StringBuffer().append(filePath).append(MQES.MQ_OK_PATH).append("/")
					.append(MQES.MQ_OK_FILE).append(MQES.TXT).toString();
			
			//加载所有已线程
			readFile.readListenThreads(path, threadManager);
			
			//启动监控
			monitor.createExecutorService(threadManager);
			
			LOGGER.info("-----Load Listener Threads End---");
		}
	}
}