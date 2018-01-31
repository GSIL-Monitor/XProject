package com.xinguang.xusercenter.common.base;

import com.xinguang.xusercenter.ldap.domain.UserLdap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServiceTicket {

	private String ticket;
	private String service;
	private UserLdap userLdap;
	
}
