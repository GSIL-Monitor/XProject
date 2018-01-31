package com.xinguang.xusercenter.controller;

import com.xinguang.xusercenter.param.LoginParam;
import com.xinguang.xusercenter.param.RegistParam;
import com.xinguang.xusercenter.param.UpdatePasswordParam;
import com.xinguang.xusercenter.service.LoginService;
import com.xinguang.xusercenter.service.PasswordService;
import com.xinguang.xusercenter.service.RegistService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 用户控制器
 * @author yangsh
 */
@Controller
public class UserController {

	@Resource
	private LoginService loginService;

	@Resource
	private RegistService registService;

	@Resource
	private PasswordService passwordService;

	/**
	 * 用户注册
	 * @return
	 */
	@RequestMapping(value = "regist", method = RequestMethod.POST)
	public ModelAndView regist(final RegistParam registParam) {
		return registService.regist(registParam);
	}

	/**
	 * 用户登录
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView login(final LoginParam loginParam, HttpSession session) {
		return loginService.login(loginParam, session);
	}

	/**
	 * 用户密码修改
	 * @return
	 */
	@RequestMapping(value = "updatePassword", method = RequestMethod.POST)
	public ModelAndView updatePassword(final UpdatePasswordParam updatePasswordParam, HttpSession session) {
		return passwordService.updatePassword(updatePasswordParam, session);
	}

}
