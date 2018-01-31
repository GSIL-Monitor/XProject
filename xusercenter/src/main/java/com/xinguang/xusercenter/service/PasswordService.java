package com.xinguang.xusercenter.service;

import com.xinguang.xusercenter.common.base.ActionType;
import com.xinguang.xusercenter.common.base.ReturnCode;
import com.xinguang.xusercenter.common.base.ReturnData;
import com.xinguang.xusercenter.common.util.ReturnUtil;
import com.xinguang.xusercenter.common.util.SHA1Util;
import com.xinguang.xusercenter.common.util.StringUtil;
import com.xinguang.xusercenter.entity.Log;
import com.xinguang.xusercenter.entity.Question;
import com.xinguang.xusercenter.entity.Security;
import com.xinguang.xusercenter.entity.User;
import com.xinguang.xusercenter.mapper.LogMapper;
import com.xinguang.xusercenter.mapper.QuestionMapper;
import com.xinguang.xusercenter.mapper.SecurityMapper;
import com.xinguang.xusercenter.mapper.UserMapper;
import com.xinguang.xusercenter.param.UpdatePasswordParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by yangsh
 */
@Service
@Slf4j
public class PasswordService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private LogMapper logMapper;

    @Resource
    private SecurityMapper securityMapper;

    @Resource
    private QuestionMapper questionMapper;

    /**
     * 用户密码修改
     * @param updatePasswordParam
     * @return
     */
    public ModelAndView updatePassword(final UpdatePasswordParam updatePasswordParam, HttpSession session) {
        ModelAndView mv = new ModelAndView();

        String username = updatePasswordParam.getUsername();
        String oldPassword = SHA1Util.getSha1(updatePasswordParam.getOpassword());
        String newPassword = SHA1Util.getSha1(updatePasswordParam.getNpassword());

        User user = userMapper.getUserByUsernameAndPassword(username, oldPassword);
        if (user == null) {
            mv.setViewName("redirect:topassword");
            mv.addObject("info", "原密码有误");
            return mv;
        }

        // 修改密码
        user.setPassword(newPassword);
        user.setUpdateTime(new Date());

        userMapper.updateUser(user);

        // 新增日志
        Log logx = new Log();

        logx.setId(UUID.randomUUID().toString());
        logx.setUserId(user.getId());
        logx.setUsername(username);
        logx.setActionType(ActionType.UPDATEPASSWORD);
        logx.setCreateTime(new Date());

        logMapper.addLog(logx);

        session.removeAttribute("user");

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
        } catch (SessionException ise) {
            log.debug("Encountered session exception during logout. This can generally safely be ignored.", ise);
        }

        mv.setViewName("redirect:tologin");
        mv.addObject("username", username);
        mv.addObject("info", "密码修改成功，请重新登录！");

        return mv;
    }

    /**
     * 获取密保问题
     * @param username
     * @return
     */
    public ReturnData getPasswordQuestion(final String username) {
        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            return ReturnUtil.fail(ReturnCode.UNAME_ERROR);
        }

        Security security = securityMapper.getSecurityById(user.getId()); // 安全ID = 用户ID

        Integer questiononeId = security.getQuestiononeId(); // 问题1ID
        Integer questiontwoId = security.getQuestiontwoId(); // 问题2ID
        if (questiononeId == null || questiontwoId == null) {
            return ReturnUtil.fail(ReturnCode.UNSET_PASSWORD_QUESTION);
        }

        Question questionone = questionMapper.getQuestionById(questiononeId); // 问题1
        Question questiontwo = questionMapper.getQuestionById(questiontwoId); // 问题2

        Map<String, Object> data = new HashMap<>();

        data.put("securityId", security.getId());

        data.put("questionone", questionone.getContent());
        data.put("questiononePrompt", security.getQuestiononePrompt());

        data.put("questiontwo", questiontwo.getContent());
        data.put("questiontwoPrompt", security.getQuestiontwoPrompt());

        return ReturnUtil.success(data);
    }

    /**
     * 验证密保答案
     * @param securityId
     * @param answerone
     * @param answertwo
     * @return
     */
    public ReturnData validateAnswer(final String securityId, final String answerone, final String answertwo) {
        Security security = securityMapper.getSecurityById(securityId); // 安全

        if (StringUtil.isEmpty(answerone) || StringUtil.isEmpty(answertwo)) {
            return ReturnUtil.fail(ReturnCode.PARAM_ERROR);
        }

        if (answerone.equals(security.getAnswerone()) && answertwo.equals(security.getAnswertwo())) {
            return ReturnUtil.success();
        } else {
            return ReturnUtil.fail(ReturnCode.ANSWER_ERROR);
        }
    }

    /**
     * 重置密码
     * @param userId
     * @param password
     * @return
     */
    public ReturnData resetPassword(final String userId, final String password) {
        User user = userMapper.getUserById(userId);
        if (user == null) {
            log.error("user is null with id: " + userId);
            return ReturnUtil.fail(ReturnCode.USER_ID_ERROR);
        }

        // 重置密码
        user.setPassword(SHA1Util.getSha1(password));
        user.setUpdateTime(new Date());

        userMapper.updateUser(user);

        String username = user.getUsername(); // 用户名

        // 新增日志
        Log logx = new Log();

        logx.setId(UUID.randomUUID().toString());
        logx.setUserId(userId);
        logx.setUsername(username);
        logx.setActionType(ActionType.RESETPASSWORD);
        logx.setCreateTime(new Date());

        logMapper.addLog(logx);

        Map<String, Object> data = new HashMap<>();

        data.put("username", username);

        return ReturnUtil.success(data);
    }

}
