package com.xinguang.xusercenter.common.store;

import java.util.HashMap;
import java.util.Map;

import com.xinguang.xusercenter.common.base.ServiceTicket;

public class ServiceTicketStore {

	private static Map<String, ServiceTicket> stMap = new HashMap<>();
	
	public static void add(String ticket, ServiceTicket st) {
		stMap.put(ticket, st);
	}
	
	public static ServiceTicket get(String ticket) {
		return stMap.get(ticket);
	}
	
	public static void remove(String ticket) {
		stMap.remove(ticket);
	}
	
}
