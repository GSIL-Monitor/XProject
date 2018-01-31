package com.xinguang.xusercenter.service;

import com.xinguang.xusercenter.common.base.ActionType;
import com.xinguang.xusercenter.common.base.Constants;
import com.xinguang.xusercenter.common.base.ReturnCode;
import com.xinguang.xusercenter.common.base.ReturnData;
import com.xinguang.xusercenter.common.store.LoginTicketStore;
import com.xinguang.xusercenter.common.util.LdapPasswordUtil;
import com.xinguang.xusercenter.common.util.ReturnUtil;
import com.xinguang.xusercenter.common.util.StringUtil;
import com.xinguang.xusercenter.entity.Log;
import com.xinguang.xusercenter.entity.Question;
import com.xinguang.xusercenter.entity.Security;
import com.xinguang.xusercenter.ldap.dao.UserDao;
import com.xinguang.xusercenter.ldap.domain.UserLdap;
import com.xinguang.xusercenter.mapper.LogMapper;
import com.xinguang.xusercenter.mapper.QuestionMapper;
import com.xinguang.xusercenter.mapper.SecurityMapper;
import com.xinguang.xusercenter.param.SetQuestionParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by yangsh
 */
@Slf4j
@Service
public class SecurityService {

    @Resource
    private SecurityMapper securityMapper;

    @Resource
    private UserDao userDao;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private LogMapper logMapper;

    /**
     * 获取安全信息
     * @param session
     * @return
     */
    public ReturnData getSecurity(HttpSession session) {
        UserLdap ul = (UserLdap) session.getAttribute(Constants.SESSION_USER_KEY);

        Security security = securityMapper.getSecurityById(ul.getUidNumber());

        if (security == null) {
            // 新增安全
            security = new Security();

            security.setId(ul.getUidNumber());
            security.setCreateTime(new Date());

            securityMapper.addSecurity(security);
        }

        String phone = security.getPhone();
        if (StringUtil.isNotEmpty(phone)) {
            security.setPhone(phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
        }

        String email = security.getEmail();
        if (StringUtil.isNotEmpty(email)) {
            String[] es = email.split("@");
            security.setEmail(es[0].substring(0, 1) + "***@" + es[1]);
        }

        Map<String, Object> data = new HashMap<>();

        data.put("security", security);

        return ReturnUtil.success(data);
    }

    /**
     * 设置密保问题
     * @param setQuestionParam
     * @return
     */
    public ReturnData setQuestion(SetQuestionParam setQuestionParam) {
        securityMapper.updateSecurityForSetQuestion(
                setQuestionParam.getId(),
                setQuestionParam.getQuestiononeId(),
                setQuestionParam.getAnswerone(),
                setQuestionParam.getQuestiononePrompt(),
                setQuestionParam.getQuestiontwoId(),
                setQuestionParam.getAnswertwo(),
                setQuestionParam.getQuestiontwoPrompt());
        return ReturnUtil.success();
    }

    /**
     * 获取密保问题
     * @param username
     * @return
     */
    public ReturnData getPasswordQuestion(final String uid) {
        if (StringUtil.isEmpty(uid)) {
            return ReturnUtil.fail(ReturnCode.PARAM_INCOMPLETE);
        }

        List<UserLdap> uls = userDao.search("", "(&(objectclass=inetOrgPerson)(uid=" + uid + "))");
        if (uls.isEmpty()) {
            return ReturnUtil.fail(ReturnCode.UNAME_ERROR);
        }

        UserLdap ul = uls.get(0);

        Security security = securityMapper.getSecurityById(ul.getUidNumber()); // 安全ID = 用户ID

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
    public ReturnData validateAnswer(final String securityId, final String answerone, final String answertwo, HttpSession session) {
        if (StringUtil.isEmpty(securityId) || StringUtil.isEmpty(answerone) || StringUtil.isEmpty(answertwo)) {
            return ReturnUtil.fail(ReturnCode.PARAM_INCOMPLETE);
        }

        Security security = securityMapper.getSecurityById(securityId); // 安全

        if (StringUtil.isEmpty(answerone) || StringUtil.isEmpty(answertwo)) {
            return ReturnUtil.fail(ReturnCode.PARAM_ERROR);
        }

        if (answerone.equals(security.getAnswerone()) && answertwo.equals(security.getAnswertwo())) {
            session.setAttribute("validateAnswerResult", "SUCCESS");
            return ReturnUtil.success();
        } else {
            session.setAttribute("validateAnswerResult", "FAIL");
            return ReturnUtil.fail(ReturnCode.ANSWER_ERROR);
        }
    }

    /**
     * 重置密码
     * @param userId
     * @param password
     * @return
     */
    public ReturnData resetPassword(final String uid, final String password, HttpSession session) {
        if (StringUtil.isEmpty(uid) || StringUtil.isEmpty(password)) {
            return ReturnUtil.fail(ReturnCode.PARAM_INCOMPLETE);
        }

        String validateAnswerResult = (String) session.getAttribute("validateAnswerResult");

        if (!"SUCCESS".equals(validateAnswerResult)) {
            return ReturnUtil.fail(ReturnCode.INVALID_REQUEST);
        }
        List<UserLdap> uls = userDao.search("", "(&(objectclass=inetOrgPerson)(uid=" + uid + "))");
        if (uls.isEmpty()) {
            return ReturnUtil.fail(ReturnCode.UNAME_ERROR);
        }

        UserLdap ul = uls.get(0);

        String userDN = "uid=" + uid + ",ou=People,o=" + ul.getO();

        log.info("dn=[" + userDN + "]");

        Map<String, Object> param = new HashMap<>();

        param.put("userPassword", LdapPasswordUtil.getSSHA(password));

        userDao.modfiyAttrs(userDN, param);

        // 新增日志
        Log logx = new Log();

        logx.setId(UUID.randomUUID().toString());
        logx.setUserId(ul.getUidNumber());
        logx.setUsername(uid);
        logx.setActionType(ActionType.RESETPASSWORD);
        logx.setCreateTime(new Date());

        logMapper.add(logx);

        session.removeAttribute("validateAnswerResult");

        return ReturnUtil.success();
    }

}
