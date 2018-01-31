package com.xinguang.xusercenter.service;

import com.xinguang.xusercenter.common.base.ActionType;
import com.xinguang.xusercenter.common.base.Constants;
import com.xinguang.xusercenter.common.util.SHA1Util;
import com.xinguang.xusercenter.common.util.StringUtil;
import com.xinguang.xusercenter.entity.Log;
import com.xinguang.xusercenter.entity.Security;
import com.xinguang.xusercenter.entity.User;
import com.xinguang.xusercenter.mapper.LogMapper;
import com.xinguang.xusercenter.mapper.SecurityMapper;
import com.xinguang.xusercenter.mapper.UserMapper;
import com.xinguang.xusercenter.param.RegistParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * Created by yangsh
 */
@Service
@Slf4j
public class RegistService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private SecurityMapper securityMapper;

    @Resource
    private LogMapper logMapper;

    /**
     * 用户注册
     * @param registParam
     * @return
     */
    @Transactional
    public ModelAndView regist(final RegistParam registParam) {
        ModelAndView mv = new ModelAndView();

        String username = registParam.getUsername(); // 用户名
        String password = SHA1Util.getSha1(registParam.getPassword()); // 密码
        String name = registParam.getName(); // 姓名
        String phone = registParam.getPhone(); // 手机
        String email = registParam.getEmail(); // 邮箱

        mv.addObject("username", username);

        // 非空验证
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            return this.fail(username, name, phone, email, "账号信息不能为空", mv);
        }

        username = username.trim();
        password = password.trim();

        if (userMapper.isExistByUsername(username)) {
            log.info("username " + username + " has exist");
            return this.fail(username, name, phone, email, "用户名已存在", mv);
        }

        if (StringUtil.isNotEmpty(phone)) {
            if (userMapper.isExistByPhone(phone)) {
                log.info("phone " + phone + " has exist");
                return this.fail(username, name, phone, email, "手机已存在", mv);
            }
        }

        if (StringUtil.isNotEmpty(email)) {
            if (userMapper.isExistByEmail(email)) {
                log.info("email " + email + " has exist");
                return this.fail(username, name, phone, email, "邮箱已存在", mv);
            }
        }

        Date now = new Date(); // 当前时间

        String userId = String.valueOf(now.getTime()); // 用户ID

        // 新增日志
        Log logx = new Log();

        logx.setId(UUID.randomUUID().toString());
        logx.setUserId(userId);
        logx.setUsername(username);
        logx.setActionType(ActionType.REGIST);
        logx.setCreateTime(now);

        logMapper.addLog(logx);

        // 新增安全
        Security security = new Security();

        security.setId(userId);
        security.setCreateTime(now);

        securityMapper.addSecurity(security);

        // 新增用户
        User user = new User();

        user.setId(userId);
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setState(Constants.USER_STATE_ENABLE);
        user.setCreateTime(now);

        userMapper.addUser(user);

        log.info("user " + username + " regist success");

        return this.success(username, mv);
    }

    // 注册成功
    private ModelAndView success(final String username, final ModelAndView mv) {
        log.info("redirect to login page");
        mv.addObject("username", username);
        mv.addObject("info", "用户注册成功");
        mv.setViewName("redirect:tologin");
        return mv;
    }

    // 注册失败
    private ModelAndView fail(final String username, final String name, final String phone, final String email, final String info, final ModelAndView mv) {
        mv.addObject("username", username);
        mv.addObject("name", name);
        mv.addObject("phone", phone);
        mv.addObject("email", email);
        mv.addObject("info", info);
        mv.setViewName("redirect:toregist");
        return mv;
    }

}
