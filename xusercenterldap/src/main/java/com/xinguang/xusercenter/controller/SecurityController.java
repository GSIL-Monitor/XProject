package com.xinguang.xusercenter.controller;

import com.xinguang.xusercenter.common.base.ReturnData;
import com.xinguang.xusercenter.param.SetQuestionParam;
import com.xinguang.xusercenter.service.QuestionService;
import com.xinguang.xusercenter.service.SecurityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 安全控制器
 *
 * Created by yangsh
 */
@RestController
public class SecurityController {

    @Resource
    private QuestionService questionService;

    @Resource
    private SecurityService securityService;

    /**
     * 获取密保问题
     * @return
     */
    @RequestMapping(value = "getPasswordQuestion", method = RequestMethod.POST)
    public ReturnData getPasswordQuestion(final String uid) {
        return securityService.getPasswordQuestion(uid);
    }

    /**
     * 验证密保答案
     * @return
     */
    @RequestMapping(value = "validateAnswer", method = RequestMethod.POST)
    public ReturnData validateAnswer(
            final String securityId,
            final String answerone,
            final String answertwo,
            HttpSession session) {
        return securityService.validateAnswer(securityId, answerone, answertwo, session);
    }

    /**
     * 重置密码
     * @return
     */
    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    public ReturnData resetPassword(final String uid, final String o, final String password, HttpSession session) {
        return securityService.resetPassword(uid, password, session);
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

}
