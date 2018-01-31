package com.baidu.disconf.web.web.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.baidu.disconf.web.service.app.bo.App;
import com.baidu.disconf.web.service.user.bo.User;
import com.baidu.disconf.web.service.user.constant.UserConstant;
import com.baidu.disconf.web.service.user.dto.Visitor;
import com.baidu.disconf.web.service.user.service.UserMgr;
import com.baidu.disconf.web.service.user.vo.VisitorVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.disconf.web.service.app.form.AppNewForm;
import com.baidu.disconf.web.service.app.service.AppMgr;
import com.baidu.disconf.web.service.app.vo.AppListVo;
import com.baidu.disconf.web.web.app.validator.AppValidator;
import com.baidu.dsp.common.constant.WebConstants;
import com.baidu.dsp.common.controller.BaseController;
import com.baidu.dsp.common.vo.JsonObjectBase;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/app")
public class AppController extends BaseController {

    protected static final Logger LOG = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private AppMgr appMgr;

    @Autowired
    private AppValidator appValidator;

    @Autowired
    private UserMgr userMgr;

    /**
     * list
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase list() {

        List<AppListVo> appListVos = appMgr.getAuthAppVoList();

        return buildListSuccess(appListVos, appListVos.size());
    }

    /**
     * create
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBase create(@Valid AppNewForm appNewForm, HttpServletRequest request) {

        LOG.info(appNewForm.toString());

        appValidator.validateCreate(appNewForm);

        App app = appMgr.create(appNewForm);

        // -- modify start
        VisitorVo curVisitor = userMgr.getCurVisitor();

        Long userId = curVisitor.getId();
        String name = curVisitor.getName();

        User user = userMgr.getUser(userId);
        LOG.info("user: " + user);

        if (!"admin".equals(name)) {
            Integer roleId = user.getRoleId();
            if (roleId != null && roleId.equals(1)) {
                String ownApps = user.getOwnApps();
                if (ownApps == null || ownApps.isEmpty()) {
                    ownApps = String.valueOf(app.getId());
                } else {
                    ownApps = ownApps + "," + app.getId();
                }

                try {
                    userMgr.setAuth(userId, user.getOwnEnvs(), ownApps, roleId);

                    Visitor visitor = new Visitor();

                    visitor.setId(user.getId());
                    visitor.setLoginUserId(user.getId());
                    visitor.setLoginUserName(user.getName());
                    visitor.setRoleId(user.getRoleId());
                    visitor.setAppIds(ownApps);
                    visitor.setEnvIds(user.getOwnEnvs());

                    request.getSession().setAttribute(UserConstant.USER_KEY, visitor);
                } catch (Exception e) {
                    LOG.error("update user ownEnvs error");
                }
            }
        }
        // -- modify end

        return buildSuccess("创建成功");
    }

}
