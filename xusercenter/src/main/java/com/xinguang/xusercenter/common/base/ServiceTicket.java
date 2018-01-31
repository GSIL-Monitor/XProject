package com.xinguang.xusercenter.common.base;

import com.xinguang.xusercenter.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServiceTicket {

	private String ticket;
	private String service;
	private User user;
	
}
