package com.xinguang.xusercenter.controller;

import java.util.HashMap;
import java.util.Map;

import com.xinguang.xusercenter.common.store.LoginTicketStore;
import com.xinguang.xusercenter.ldap.domain.CompanyLdap;
import com.xinguang.xusercenter.ldap.domain.UserLdap;
import com.xinguang.xusercenter.service.LdapService;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 票据控制器
 *
 * Created by yangsh
 */
@Slf4j
@RestController
public class TicketController {

	@Resource
	private LdapService ldapService;

	/**
	 * 获取登录票据
	 * @return
	 */
	@RequestMapping(value = "getLoginTicket", method = RequestMethod.POST)
	public String getLoginTicket() {
		return LoginTicketStore.add();
	}

	/**
	 * 服务票据验证
	 * @param service 应用地址
	 * @param ticket 服务票据
	 * @return
	 */
	@RequestMapping(value = "validate", method = RequestMethod.POST)
	public ReturnData validate(final String service, final String ticket, HttpSession session) {
		ServiceTicket st = ServiceTicketStore.get(ticket);
		if (st != null) {
			if (StringUtil.isEquals(ticket, st.getTicket()) && StringUtil.isEquals(service, st.getService())) {
				Map<String, Object> data = new HashMap<>();

				UserLdap user = st.getUserLdap();

				String uid = user.getUid();

				data.put("user", user); // 用户
				if ("admin".equals(uid)) {
					data.put("company", new CompanyLdap()); // 用户所属公司
				} else {
					data.put("company", ldapService.getCompanyLdap(user.getO())); // 用户所属公司
				}

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
