package com.baidu.disconf.web.web.auth;

import com.baidu.disconf.web.service.sign.service.SignMgr;
import com.baidu.disconf.web.service.user.bo.User;
import com.baidu.disconf.web.web.auth.constant.LoginConstant;
import com.baidu.disconf.web.web.auth.login.RedisLogin;
import com.baidu.disconf.web.web.auth.validator.AuthValidator;
import com.baidu.dsp.common.annotation.NoAuth;
import com.baidu.dsp.common.controller.BaseController;
import com.xinguang.casclient.common.CasConstants;
import com.xinguang.casclient.common.UserInfo;
import com.xinguang.casclient.util.CasUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by yangsh
 */
@Controller
public class CallbackController extends BaseController {

    protected static final Logger LOG = LoggerFactory.getLogger(CallbackController.class);

    @Autowired
    private SignMgr signMgr;

    @Autowired
    private RedisLogin redisLogin;

    @Autowired
    private AuthValidator authValidator;

    /**
     * 用户中心回调接口
     *
     * @param ticket
     * @param model
     * @param session
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public String callback(final String ticket, final ModelMap model, HttpSession session, HttpServletRequest request) {
        System.out.println("callback start");

        if (ticket == null) {
            LOG.error("ticket is null");
            return "redirect:/disconf/error.html";
        }

        UserInfo userInfo = CasUtil.getUserInfo(ticket);

        if (userInfo == null) {
            LOG.error("user is null");
            return "redirect:/disconf/error.html";
        }

        String username = userInfo.getUsername();
        LOG.info("username " + username + " auth success");

        String redirectUrl = "redirect:/disconf/main.html";

        // 过期时间
        int expireTime = LoginConstant.SESSION_EXPIRE_TIME;

        // 数据库登录
        User user = signMgr.signin(username);
        if (user == null) {
            user = new User();

            user.setName(username);
            user.setRoleId(2);

            user = signMgr.addUser(user);
        }

        // redis login
        redisLogin.login(request, user, expireTime);

        UsernamePasswordToken token = new UsernamePasswordToken(username, UUID.randomUUID().toString());
        Subject subject = SecurityUtils.getSubject();

        subject.login(token);

        if ("admin".equals(username)) {
            session.setAttribute(CasConstants.IS_ADMIN, true);
        } else {
            session.setAttribute(CasConstants.IS_ADMIN, false);
        }
        session.setAttribute(CasConstants.USERNAME, username);

        return redirectUrl;

    }

}
