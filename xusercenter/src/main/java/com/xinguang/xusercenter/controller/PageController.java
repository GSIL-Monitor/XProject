package com.xinguang.xusercenter.controller;

import com.xinguang.xusercenter.common.store.LoginTicketStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面控制器
 * @author yangsh
 */
@Controller
@Slf4j
public class PageController {
	
	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping(value = "tologin", method = RequestMethod.GET)
	public ModelAndView tologin(final String service) {
		String lt = LoginTicketStore.add();
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("service", service);
		mv.addObject("LT", lt);
		mv.setViewName("login");
		log.info("show login page");
		return mv;
	}

	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping(value = "toregist", method = RequestMethod.GET)
	public String toregist() {
		log.info("show regist page");
		return "regist";
	}

	/**
	 * 主页面
	 * @return
	 */
	@RequestMapping(value = "tomain", method = RequestMethod.GET)
	public String tomain() {
		log.info("show main page");
		return "main";
	}

	/**
	 * 用户页面
	 * @return
	 */
	@RequestMapping(value = "touser", method = RequestMethod.GET)
	public ModelAndView touser() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user");
		log.info("show user page");
		return mv;
	}

	/**
	 * 密码修改页面
	 * @return
	 */
	@RequestMapping(value = "topassword", method = RequestMethod.GET)
	public ModelAndView topassword() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("password");
		log.info("show password page");
		return mv;
	}

	/**
	 * 错误页面
	 * @return
	 */
	@RequestMapping(value = "toerror", method = RequestMethod.GET)
	public ModelAndView toerror() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("error");
		log.info("show error page");
		return mv;
	}

	/**
	 * 忘记密码页面
	 */
	@RequestMapping(value = "toforgetpassword", method = RequestMethod.GET)
	public ModelAndView toforgetpassword() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("forgetpassword");
		log.info("show forgetpassword page");
		return mv;
	}

	/**
	 * 退出确认页面
	 */
	@RequestMapping(value = "tologoutcfm", method = RequestMethod.GET)
	public ModelAndView tologoutcfm() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("modal/common/logout");
		log.info("show tologoutcfm page");
		return mv;
	}

	/**
	 * 账号安全页面
	 */
	@RequestMapping(value = "tosecurity", method = RequestMethod.GET)
	public ModelAndView tosecurity() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("security");
		log.info("show security page");
		return mv;
	}
	
}
