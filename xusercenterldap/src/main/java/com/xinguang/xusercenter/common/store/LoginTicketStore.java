package com.xinguang.xusercenter.common.store;

import com.xinguang.xusercenter.common.base.Constants;
import com.xinguang.xusercenter.common.util.TicketUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Slf4j
public class LoginTicketStore {

	private static Queue<String> loginTicketQueue = new LinkedList<>();
	
	public static String add() {
		log.info("loginTicketQueue size: " + loginTicketQueue.size());
		if (loginTicketQueue.size() >= Constants.LT_MAX) {
			String oldLT = loginTicketQueue.poll(); // 移除第一个元素
			log.info("remove old " + oldLT + " from memory");
		}
		String lt = TicketUtil.getLoginTicket();
		while (isExist(lt)) {
			lt = TicketUtil.getLoginTicket();
		}
		log.info("add " + lt + " to memory");
		loginTicketQueue.offer(lt);
		return lt;
	}

	public static Map<String, Object> getMap() {
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("lt", add());

		return data;
	}
	
	public static boolean isExist(String lt) {
		if (lt == null) {
			return false;
		}
		return loginTicketQueue.contains(lt);
	}
	
	public static void remove(String lt) {
		log.info("remove " + lt + " from memory");
		loginTicketQueue.remove(lt);
	}
	
}
