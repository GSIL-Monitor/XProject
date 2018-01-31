package com.xinguang.xusercenter.controller;

import com.xinguang.xusercenter.common.base.ReturnData;
import com.xinguang.xusercenter.param.SetQuestionParam;
import com.xinguang.xusercenter.param.UpdateUserInfoParam;
import com.xinguang.xusercenter.service.PasswordService;
import com.xinguang.xusercenter.service.QuestionService;
import com.xinguang.xusercenter.service.SecurityService;
import com.xinguang.xusercenter.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by yangsh
 */
@RestController
public class AjaxController {

    @Resource
    private PasswordService passwordService;

    @Resource
    private QuestionService questionService;

    @Resource
    private SecurityService securityService;

    @Resource
    private UserService serService;

    /**
     * 获取密保问题
     * @return
     */
    @RequestMapping(value = "getPasswordQuestion", method = RequestMethod.POST)
    public ReturnData getPasswordQuestion(final String username) {
        return passwordService.getPasswordQuestion(username);
    }

    /**
     * 验证密保答案
     * @return
     */
    @RequestMapping(value = "validateAnswer", method = RequestMethod.POST)
    public ReturnData validateAnswer(
            final String securityId,
            final String answerone,
            final String answertwo) {
        return passwordService.validateAnswer(securityId, answerone, answertwo);
    }

    /**
     * 重置密码
     * @return
     */
    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    public ReturnData resetPassword(final String userId, final String password) {
        return passwordService.resetPassword(userId, password);
    }

    /**
     * 获取安全
     * @return
     */
    @RequestMapping(value = "getSecurity", method = RequestMethod.POST)
    public ReturnData getSecurity(HttpSession session) {
        return securityService.getSecurity(session);
    }

    /**
     * 获取密保问题列表
     * @return
     */
    @RequestMapping(value = "getQuestions", method = RequestMethod.POST)
    public ReturnData getQuestions() {
        return questionService.getQuestions();
    }

    /**
     * 设置密保问题
     * @return
     */
    @RequestMapping(value = "setQuestion", method = RequestMethod.POST)
    public ReturnData setQuestion(SetQuestionParam setQuestionParam) {
        return securityService.setQuestion(setQuestionParam);
    }

    /**
     * 修改用户信息
     * @return
     */
    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    public ReturnData updateUserInfo(UpdateUserInfoParam updateUserInfoParam, HttpSession session) {
        return serService.updateUserInfo(updateUserInfoParam, session);
    }

}
