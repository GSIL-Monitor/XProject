package com.xinguang.xusercenter.controller;

import com.xinguang.xusercenter.common.base.ReturnData;
import com.xinguang.xusercenter.param.LoginParam;
import com.xinguang.xusercenter.param.RegistParam;
import com.xinguang.xusercenter.param.UpdatePasswordParam;
import com.xinguang.xusercenter.param.UpdateUserInfoParam;
import com.xinguang.xusercenter.service.LdapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * LDAP 控制器
 *
 * Created by yangsh
 */
@Slf4j
@RestController
public class LdapController {

    @Resource
    private LdapService ldapService;

    /**
     * 用户注册 (LDAP)
     * @return
     */
    @RequestMapping(value = "regist", method = RequestMethod.POST)
    public ReturnData regist(final RegistParam registParam) {
        return ldapService.regist(registParam);
    }

    /**
     * 用户登录 (LDAP)
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ReturnData login(final LoginParam loginParam, HttpSession session) {
        log.info(loginParam.toString());
        return ldapService.login(loginParam, session);
    }

    /**
     * 修改用户密码 (LDAP)
     * @param updatePasswordParam
     * @return
     */
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public ReturnData updatePassword(final UpdatePasswordParam updatePasswordParam) {
        log.info(updatePasswordParam.toString());
        return ldapService.updatePassword(updatePasswordParam);
    }

    /**
     * 修改用户信息 (LDAP)
     * @return
     */
    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    public ReturnData updateUserInfo(final UpdateUserInfoParam updateUserInfoParam, HttpSession session) {
        return ldapService.updateUserInfo(updateUserInfoParam, session);
    }

}
