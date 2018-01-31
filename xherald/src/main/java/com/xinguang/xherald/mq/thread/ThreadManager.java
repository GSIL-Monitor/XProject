/**
 * 
 */
package com.xinguang.xherald.mq.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinguang.xherald.mq.beans.MQConnection;
import com.xinguang.xherald.mq.constant.MQES;

/**
 * 
 * ClassName: ThreadManager <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年9月13日 下午1:50:14 <br/>
 *
 * @author Administrator-lengxing
 * @version 
 * @since JDK 1.7
 */
@Component
public class ThreadManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadManager.class);

	@Autowired
	private MyThreadFactory threadFactory;

	public static Map<Map<String, String>, List<Thread>> msgThreadsMap = new ConcurrentHashMap<Map<String, String>, List<Thread>>();

	public ThreadManager() {
	}

	public synchronized boolean createThread(Map<String, String> msg, int num) {
		if (num > MQES.THREADNUM) {
			return false;
		}
		for (int i = 0; i < num; i++) {
			Thread newThread;
			try {
				Task task = new Task();
				newThread = threadFactory.ctreatThread(task.setConfig(msg));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOGGER.error("create ListenThread error. function={}, input={}, errorMessage={}", "createThread", msg, e);
				return false;
			}
			if (msgThreadsMap.containsKey(msg)) {
				List<Thread> threadList = msgThreadsMap.get(msg);
				threadList.add(newThread);
				msgThreadsMap.put(msg, threadList);
			} else {
				List<Thread> threadList = new ArrayList<Thread>();
				threadList.add(newThread);
				msgThreadsMap.put(msg, threadList);
			}
			newThread.start();
		}
		return true;
	}
	
	public static synchronized Map<Map<String, String>, List<Thread>> getMsgMapInstance() {
		if (msgThreadsMap == null) {
			msgThreadsMap = new ConcurrentHashMap<Map<String, String>, List<Thread>>();
		}
		return msgThreadsMap;
	}
}
