/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.dangdang.ddframe.job.lite.console.controller;

import com.dangdang.ddframe.job.lite.console.domain.RegistryCenterConfiguration;
import com.dangdang.ddframe.job.lite.console.service.RegistryCenterService;
import com.google.common.base.Optional;
import com.xinguang.casclient.common.CasConstants;
import com.xinguang.casclient.common.UserInfo;
import com.xinguang.casclient.util.CasUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@RequestMapping("/")
@SessionAttributes(RegistryCenterController.REG_CENTER_CONFIG_KEY)
@Slf4j
public class DashboardController {

	@Resource
	private RegistryCenterService regCenterService;

	@RequestMapping(method = RequestMethod.GET)
	public String homepage() {
		return "redirect:main";
	}

	@RequestMapping(value = "callback", method = RequestMethod.GET)
	public String callback(final String ticket, final ModelMap model, HttpSession session) {
		if (ticket == null) {
			return "redirect:error";
		}

		UserInfo userInfo = CasUtil.getUserInfo(ticket);

		if (userInfo == null) {
			log.error("user is null");
			return "redirect:error";
		}

		String username = userInfo.getUsername();
		log.info("username " + username + " auth success");

		UsernamePasswordToken token = new UsernamePasswordToken(username, UUID.randomUUID().toString());
		Subject subject = SecurityUtils.getSubject();

		subject.login(token);

		if ("admin".equals(username)) {
			regCenterService.unLoad();
			session.setAttribute(CasConstants.IS_ADMIN, true);
		} else {
			boolean flag = regCenterService.isInvalid(username);
			if (flag) {
				return "redirect:invalid";
			}
			regCenterService.loadByName(username);
			session.setAttribute(CasConstants.IS_ADMIN, false);
		}
		session.setAttribute(CasConstants.USERNAME, username);

		Optional<RegistryCenterConfiguration> activatedRegCenterConfig = regCenterService.loadActivated();
		if (activatedRegCenterConfig.isPresent()) {
			model.put(RegistryCenterController.REG_CENTER_CONFIG_KEY, activatedRegCenterConfig.get());
			return "redirect:overview";
		}

		return "redirect:main";
	}

	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(final ModelMap model) {
		model.put("activeTab", 1);
		return "registry_center";
	}

	@RequestMapping(value = "error", method = RequestMethod.GET)
	public String error(final ModelMap model) {
		return "error";
	}

	@RequestMapping(value = "invalid", method = RequestMethod.GET)
	public String invalid(final ModelMap model) {
		return "invalid";
	}

	@RequestMapping(value = "registry_center_page", method = RequestMethod.GET)
	public String registryCenterPage(final ModelMap model) {
		model.put("activeTab", 1);
		return "registry_center";
	}

	@RequestMapping(value = "job_detail", method = RequestMethod.GET)
	public String jobDetail(@RequestParam final String jobName, @RequestParam final String jobType,
			final ModelMap model) {
		model.put("jobName", jobName);
		model.put("jobType", jobType);
		return "job_detail";
	}

	@RequestMapping(value = "server_detail", method = RequestMethod.GET)
	public String serverDetail(@RequestParam final String serverIp, final ModelMap model) {
		model.put("serverIp", serverIp);
		return "server_detail";
	}

	@RequestMapping(value = "overview", method = RequestMethod.GET)
	public String overview(final ModelMap model) {
		model.put("activeTab", 0);
		return "overview";
	}

	@RequestMapping(value = "help", method = RequestMethod.GET)
	public String help(final ModelMap model) {
		model.put("activeTab", 2);
		return "help";
	}
}
