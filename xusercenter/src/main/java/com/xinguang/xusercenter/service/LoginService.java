package com.xinguang.xusercenter.service;

import com.xinguang.xusercenter.common.base.ActionType;
import com.xinguang.xusercenter.common.base.Constants;
import com.xinguang.xusercenter.common.base.ServiceTicket;
import com.xinguang.xusercenter.common.store.LoginTicketStore;
import com.xinguang.xusercenter.common.store.ServiceTicketStore;
import com.xinguang.xusercenter.common.util.SHA1Util;
import com.xinguang.xusercenter.common.util.StringUtil;
import com.xinguang.xusercenter.common.util.TicketUtil;
import com.xinguang.xusercenter.entity.Log;
import com.xinguang.xusercenter.entity.User;
import com.xinguang.xusercenter.mapper.LogMapper;
import com.xinguang.xusercenter.mapper.UserMapper;
import com.xinguang.xusercenter.param.LoginParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

/**
 * 登录服务类
 * @author yangsh
 */
@Service
@Slf4j
public class LoginService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private LogMapper logMapper;

	/**
	 * 用户登录
	 * @param loginParam
	 * @param session
	 * @return
	 */
	public ModelAndView login(final LoginParam loginParam, HttpSession session) {
		ModelAndView mv = new ModelAndView();

		String lt = loginParam.getLt();
		boolean flag = this.validateLoginTicket(lt);
		if (!flag) {
			log.error("validate " + lt + " fail");
			mv.setViewName("redirect:toerror");
			return mv;
		}
		log.error("validate " + lt + " success");
		LoginTicketStore.remove(lt);

		String username = loginParam.getUsername();
		String password = SHA1Util.getSha1(loginParam.getPassword());
		String service = loginParam.getService();

		User user = userMapper.getUserByUsername(username);
		if (user != null) {
			if (Constants.USER_STATE_DISABLE.equals(user.getState())) {
				log.info("user " + username + " login fail: username disable");
				return userDisableError(username, service, mv);
			}
		}

		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			log.info("user " + username + " login fail: username error");
			return this.usernameError(service, mv);
		} catch (IncorrectCredentialsException e) {
			log.info("user " + username + " login fail: password error");
			return this.passwordError(username, service, mv);
		} catch (AuthenticationException e) {
			log.error("user " + username + " login fail: other error");
			return this.otherError(username, password, service, mv);
		}
		
		if (subject.isAuthenticated()) {
			// 认证成功
			log.info("user " + username + " login success");
			return this.success(user, service, mv, session);
		} else {
			// 认证失败
			log.error("user " + username + " login fail: unknown error");
			return this.unknownError(username, password, service, mv);
		}
	}
	
	private ModelAndView usernameError(final String service, final ModelAndView mv) {
		return this.fail("tologin", "", "", service, "用户名有误", mv);
	}
	
	private ModelAndView passwordError(final String username, final String service, final ModelAndView mv) {
		return this.fail("tologin", username, "", service, "密码有误", mv);
	}

	private ModelAndView userDisableError(final String username, final String service, final ModelAndView mv) {
		return this.fail("tologin", username, "", service, "该账号已被禁用", mv);
	}
	
	private ModelAndView otherError(final String username, final String password, final String service, final ModelAndView mv) {
		return this.fail("toerror", username, password, service, "其它错误", mv);
	}
	
	private ModelAndView unknownError(final String username, final String password, final String service, final ModelAndView mv) {
		return this.fail("toerror", username, password, service, "未知错误", mv);
	}
	
	private ModelAndView success(final User user, final String service, final ModelAndView mv, HttpSession session) {
		String userId = user.getId(); // 用户ID
		String username = user.getUsername(); // 用户名

		String lastLoginTime = logMapper.getLastLoginTimeByUserId(userId);
		if (StringUtil.isEmpty(lastLoginTime)) {
			lastLoginTime = "此前无登录信息";
		}

		Log logx = new Log();

		logx.setId(UUID.randomUUID().toString());
		logx.setUserId(userId);
		logx.setUsername(username);
		logx.setActionType(ActionType.LOGIN);
		logx.setCreateTime(new Date());

		logMapper.addLog(logx);

		Integer loginCount = logMapper.getLoginCountByUserId(userId);

		log.info("add user " + username + " to session");
		session.setAttribute(Constants.SESSION_USER_KEY, user);
		session.setAttribute(Constants.SESSION_LAST_LOGIN_TIME, lastLoginTime);
		session.setAttribute(Constants.SESSION_LOGIN_COUNT, loginCount);

		if (StringUtil.isNotEmpty(service)) {
			String ticket = TicketUtil.getServiceTicket();
			ServiceTicket st = new ServiceTicket(ticket, service, user);
			log.info("add " + ticket + " to memory");
			ServiceTicketStore.add(ticket, st);
			mv.addObject("ticket", ticket);
			mv.setViewName("redirect:" + service);

			log.info("redirect to service: " + service);
		} else {
			mv.setViewName("redirect:tomain");
		}
		
		return mv;
	}
	
	private ModelAndView fail(final String redirect, final String username, final String password, final String service, final String info, final ModelAndView mv) {
		mv.addObject("service", service);
		mv.addObject("username", username);
		mv.addObject("password", password);
		mv.addObject("info", info);
		mv.setViewName("redirect:" + redirect);
		return mv;
	}

	/**
	 * 验证登录票据
	 * @param lt 登录票据
	 * @return
	 */
	private boolean validateLoginTicket(final String lt) {
		if (StringUtil.isEmpty(lt)) {
			return false;
		}
		return LoginTicketStore.isExist(lt);
	}
	
}
