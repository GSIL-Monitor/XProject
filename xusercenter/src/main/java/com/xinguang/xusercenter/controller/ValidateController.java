package com.xinguang.xusercenter.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xinguang.xusercenter.common.base.ReturnCode;
import com.xinguang.xusercenter.common.base.ReturnData;
import com.xinguang.xusercenter.common.base.ServiceTicket;
import com.xinguang.xusercenter.common.store.ServiceTicketStore;
import com.xinguang.xusercenter.common.util.ReturnUtil;
import com.xinguang.xusercenter.common.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 验证控制器
 * @author yangsh
 */
@RestController
@Slf4j
public class ValidateController {
	
	/**
	 * ticket 校验
	 * @return
	 */
	@RequestMapping(value = "validate", method = RequestMethod.POST)
	public ReturnData validate(final String service, final String ticket) {
		ServiceTicket st = ServiceTicketStore.get(ticket);
		if (st != null) {
			if (StringUtil.isEquals(ticket, st.getTicket()) && StringUtil.isEquals(service, st.getService())) {
				Map<String, Object> data = new HashMap<>();

				data.put("user", st.getUser()); // 用户
				
				log.info("remove " + ticket + " from memory (validate success)");
				ServiceTicketStore.remove(ticket);
				
				return ReturnUtil.success(data);
			}
		}
		
		log.info("remove " + ticket + " from memory (validate fail)");
		ServiceTicketStore.remove(ticket);
		
		return ReturnUtil.fail(ReturnCode.TICKET_ERROR);
	}
	
}
