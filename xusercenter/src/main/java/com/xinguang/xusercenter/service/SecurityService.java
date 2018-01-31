package com.xinguang.xusercenter.service;

import com.xinguang.xusercenter.common.base.Constants;
import com.xinguang.xusercenter.common.base.ReturnData;
import com.xinguang.xusercenter.common.util.ReturnUtil;
import com.xinguang.xusercenter.common.util.StringUtil;
import com.xinguang.xusercenter.entity.Security;
import com.xinguang.xusercenter.entity.User;
import com.xinguang.xusercenter.mapper.SecurityMapper;
import com.xinguang.xusercenter.param.SetQuestionParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangsh
 */
@Service
@Slf4j
public class SecurityService {

    @Resource
    private SecurityMapper securityMapper;

    /**
     * 获取安全信息
     * @param session
     * @return
     */
    public ReturnData getSecurity(HttpSession session) {
        User user = (User) session.getAttribute(Constants.SESSION_USER_KEY);

        Security security = securityMapper.getSecurityById(user.getId());

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

}
