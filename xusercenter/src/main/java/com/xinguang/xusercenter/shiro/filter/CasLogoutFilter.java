package com.xinguang.xusercenter.shiro.filter;

import com.xinguang.xusercenter.common.base.ActionType;
import com.xinguang.xusercenter.common.base.Constants;
import com.xinguang.xusercenter.common.context.SpringContext;
import com.xinguang.xusercenter.entity.Log;
import com.xinguang.xusercenter.entity.User;
import com.xinguang.xusercenter.mapper.LogMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class CasLogoutFilter extends LogoutFilter {

	@Override
	protected boolean preHandle(ServletRequest req, ServletResponse res) throws Exception {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

		String service = request.getParameter("service") + "/callback";

		session.removeAttribute(Constants.SESSION_USER_KEY);
        session.removeAttribute(Constants.SESSION_LAST_LOGIN_TIME);
        session.removeAttribute(Constants.SESSION_LOGIN_COUNT);

		Subject subject = getSubject(request, response);

        try {
            subject.logout();
        } catch (SessionException ise) {
            log.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }

        if (user != null) {
            LogMapper logMapper = SpringContext.getBean("logMapper");

            // 新增日志
            Log logx = new Log();

            logx.setId(UUID.randomUUID().toString());
            logx.setUserId(user.getId());
            logx.setUsername(user.getUsername());
            logx.setActionType(ActionType.LOGOUT);
            logx.setCreateTime(new Date());

            logMapper.addLog(logx);
        }

        String redirectUrl = null;
        if ("null/callback".equals(service)) {
            log.info("usercenter logout success");
            redirectUrl = getRedirectUrl(request, response, subject);
        } else {
            redirectUrl = getRedirectUrl(request, response, subject) + "?service=" + URLEncoder.encode(service, "UTF-8");
            log.info("service: " + service + " logout success");
        }

        log.info("redirect to: " + redirectUrl);
        
        issueRedirect(request, response, redirectUrl);
        return false;
	}

}
