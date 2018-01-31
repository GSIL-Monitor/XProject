package com.xinguang.xusercenter.shiro.filter;

import com.xinguang.xusercenter.common.base.Constants;
import com.xinguang.xusercenter.common.base.ServiceTicket;
import com.xinguang.xusercenter.common.store.ServiceTicketStore;
import com.xinguang.xusercenter.common.util.TicketUtil;
import com.xinguang.xusercenter.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class AuthFilter extends AuthorizationFilter {

	@Override
	protected boolean isAccessAllowed(
			ServletRequest req,
			ServletResponse res,
			Object mappedValue) throws Exception {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String service = req.getParameter("service");
		
		Subject subject = getSubject(request, response);
		boolean isAuthenticated = subject.isAuthenticated();
		log.info("isAuthenticated: " + isAuthenticated);
		if (isAuthenticated) {
			HttpSession session = request.getSession(true);
			User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);
			if (user != null) {
				log.info("user " + user.getUsername() + " has login");
				if (service != null) {
					String ticket = TicketUtil.getServiceTicket();
					
					ServiceTicket st = new ServiceTicket(ticket, service, user);
					log.info("add " + ticket + " to memory");
					ServiceTicketStore.add(ticket, st);
					
					super.setUnauthorizedUrl(service + "?ticket=" + ticket);
					return false;
				} else {
					super.setUnauthorizedUrl("tomain");
					return false;
				}
			} else {
				log.error("user has login but get user from session is null");
			}
		}
		
		log.info("user has not login");
		
		return true;
	}
	
}
