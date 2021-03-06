package com.baidu.disconf.web.web.auth;

import com.baidu.disconf.web.service.sign.form.SigninForm;
import com.baidu.disconf.web.service.sign.service.SignMgr;
import com.baidu.disconf.web.service.user.bo.User;
import com.baidu.disconf.web.service.user.service.UserMgr;
import com.baidu.disconf.web.service.user.service.impl.UserMgrImpl;
import com.baidu.disconf.web.service.user.vo.VisitorVo;
import com.baidu.disconf.web.web.auth.constant.LoginConstant;
import com.baidu.disconf.web.web.auth.login.RedisLogin;
import com.baidu.disconf.web.web.auth.validator.AuthValidator;
import com.baidu.dsp.common.annotation.NoAuth;
import com.baidu.dsp.common.constant.ErrorCode;
import com.baidu.dsp.common.constant.WebConstants;
import com.baidu.dsp.common.controller.BaseController;
import com.baidu.dsp.common.vo.JsonObjectBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author liaoqiqi
 * @version 2014-1-20
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/account")
public class UserController extends BaseController {

    protected static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMgr userMgr;

    @Autowired
    private AuthValidator authValidator;

    @Autowired
    private SignMgr signMgr;

    @Autowired
    private RedisLogin redisLogin;

    /**
     * GET 获取
     *
     * @param
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase get() {

        VisitorVo visitorVo = userMgr.getCurVisitor();
        if (visitorVo != null) {

            return buildSuccess("visitor", visitorVo);

        } else {

            // 没有登录啊
            return buildGlobalError("syserror.inner", ErrorCode.GLOBAL_ERROR);
        }
    }

    /**
     * 登录
     *
     * @param signin
     * @param request
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBase signin(@Valid SigninForm signin, HttpServletRequest request) {

        LOG.info(signin.toString());

        // 验证
        authValidator.validateLogin(signin);

        // 数据库登录
        User user = signMgr.signin(signin.getName());

        // 过期时间
        int expireTime = LoginConstant.SESSION_EXPIRE_TIME;
        if (signin.getRemember().equals(1)) {
            expireTime = LoginConstant.SESSION_EXPIRE_TIME2;
        }

        // redis login
        redisLogin.login(request, user, expireTime);

        VisitorVo visitorVo = userMgr.getCurVisitor();
        
        return buildSuccess("visitor", visitorVo);
    }

    /**
     * 登出
     *
     * @param request
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    public String signout(HttpServletRequest request) {

        redisLogin.logout(request);

        return "redirect:/logout";
    }

    /**
     * 用户列表
     *
     * @param request
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase list(HttpServletRequest request) {
        List<Map<String, Object>> users = userMgr.getList();
        return buildListSuccess(users, users.size());
    }

    /**
     * 用户权限列表
     *
     * @param userId
     * @param request
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/authlist", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase authlist(Long userId, HttpServletRequest request) {
        Map<String, Object> map = userMgr.getEnvAndApps(userId);
        return buildSuccess(map);
    }

    /**
     * 设置用户权限
     *
     * @param userId
     * @param request
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/setauth", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase setAuth(
            Long userId,
            String envs,
            String apps,
            Integer roleId,
            HttpServletRequest request) {
        Map<String, Object> map = userMgr.setAuth(userId, envs, apps, roleId);
        return buildSuccess(map);
    }

}
