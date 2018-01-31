package com.xinguang.xusercenter.controller;

import com.xinguang.xusercenter.service.LdapService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 页面控制器
 *
 * Created by yangsh
 */
@Controller
public class PageController {

	@Resource
	private LdapService ldapService;

	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping(value = "tologin", method = RequestMethod.GET)
	public String tologin() {
		return "login";
	}

	/**
	 * 注册页面
	 * @return
	 */
	@RequestMapping(value = "toregist", method = RequestMethod.GET)
	public ModelAndView toregist() {
		ModelAndView mv = new ModelAndView("regist");

		mv.addObject("companys", ldapService.getCompanyLdapList());

		return mv;
	}

	/**
	 * 主页面
	 * @return
	 */
	@RequestMapping(value = "tomain", method = RequestMethod.GET)
	public String tomain() {
		return "main";
	}

	/**
	 * 用户页面
	 * @return
	 */
	@RequestMapping(value = "touser", method = RequestMethod.GET)
	public String touser() {
		return "user";
	}

	/**
	 * 密码修改页面
	 * @return
	 */
	@RequestMapping(value = "topassword", method = RequestMethod.GET)
	public String topassword() {
		return "password";
	}

	/**
	 * 错误页面
	 * @return
	 */
	@RequestMapping(value = "toerror", method = RequestMethod.GET)
	public String toerror() {
		return "error";
	}

	/**
	 * 忘记密码页面
	 */
	@RequestMapping(value = "toforgetpassword", method = RequestMethod.GET)
	public String toforgetpassword() {
		return "forgetpassword";
	}

	/**
	 * 退出确认页面
	 */
	@RequestMapping(value = "tologoutcfm", method = RequestMethod.GET)
	public String tologoutcfm() {
		return "modal/common/logout";
	}

	/**
	 * 账号安全页面
	 */
	@RequestMapping(value = "tosecurity", method = RequestMethod.GET)
	public String tosecurity() {
		return "security";
	}

	/**
	 * 公司管理页面
	 * @return
	 */
	@RequestMapping(value = "tocompany", method = RequestMethod.GET)
	public ModelAndView tocompany() {
		ModelAndView mv = new ModelAndView("company");

		mv.addObject("companys", ldapService.getCompanyLdapList());

		return mv;
	}
	
}
