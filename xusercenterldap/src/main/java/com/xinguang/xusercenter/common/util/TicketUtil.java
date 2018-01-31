package com.xinguang.xusercenter.common.util;

import java.util.UUID;

public class TicketUtil {
	
	private static final String LOGIN_TICKET_PREFIX = "LT-";
	private static final String SERVICE_TICKET_PREFIX = "ST-";
	
	/**
	 * 获取登录票据
	 * @return
	 */
	public static String getLoginTicket() {
		return LOGIN_TICKET_PREFIX + MD5Util.getMD5(UUID.randomUUID().toString());
	}
	
	/**
	 * 获取服务票据
	 * @return
	 */
	public static String getServiceTicket() {
		return SERVICE_TICKET_PREFIX + MD5Util.getMD5(System.currentTimeMillis() + UUID.randomUUID().toString());
	}
	
}
