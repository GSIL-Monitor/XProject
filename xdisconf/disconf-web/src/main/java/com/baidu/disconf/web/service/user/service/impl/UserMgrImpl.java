package com.baidu.disconf.web.service.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.disconf.web.service.app.bo.App;
import com.baidu.disconf.web.service.app.dao.AppDao;
import com.baidu.disconf.web.service.env.bo.Env;
import com.baidu.disconf.web.service.env.dao.EnvDao;
import com.baidu.disconf.web.service.role.bo.Role;
import com.baidu.disconf.web.service.role.dao.RoleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.disconf.web.service.sign.utils.SignUtils;
import com.baidu.disconf.web.service.user.bo.User;
import com.baidu.disconf.web.service.user.dao.UserDao;
import com.baidu.disconf.web.service.user.dto.Visitor;
import com.baidu.disconf.web.service.user.service.UserInnerMgr;
import com.baidu.disconf.web.service.user.service.UserMgr;
import com.baidu.disconf.web.service.user.vo.VisitorVo;
import com.baidu.ub.common.commons.ThreadContext;

/**
 * @author liaoqiqi
 * @version 2013-12-5
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserMgrImpl implements UserMgr {

    protected static final Logger LOG = LoggerFactory.getLogger(UserMgrImpl.class);

    @Autowired
    private UserInnerMgr userInnerMgr;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EnvDao envDao;

    @Autowired
    private AppDao appDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public Visitor getVisitor(Long userId) {

        return userInnerMgr.getVisitor(userId);
    }

    @Override
    public VisitorVo getCurVisitor() {

        Visitor visitor = ThreadContext.getSessionVisitor();
        if (visitor == null) {
            return null;
        }

        VisitorVo visitorVo = new VisitorVo();
        visitorVo.setId(visitor.getId());
        visitorVo.setName(visitor.getLoginUserName());

        return visitorVo;
    }

    /**
     * 创建
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Long create(User user) {

        user = userDao.create(user);
        return user.getId();
    }

    /**
     *
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void create(List<User> users) {

        userDao.create(users);
    }

    @Override
    public List<User> getAll() {
        return userDao.findAll();
    }

    /**
     * @param userId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String addOneAppForUser(Long userId, int appId) {

        User user = getUser(userId);
        String ownAppIds = user.getOwnApps();
        if (ownAppIds.contains(",")) {
            ownAppIds = ownAppIds + "," + appId;

        } else {
            ownAppIds = String.valueOf(appId);
        }
        user.setOwnApps(ownAppIds);
        userDao.update(user);

        return ownAppIds;
    }

    /**
     * @param userId
     *
     * @return
     */
    @Override
    public User getUser(Long userId) {

        return userDao.get(userId);
    }

    @Override
    public List<Map<String, Object>> getList() {
        List<User> users = userDao.findAll();

        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map = null;
        String ownEnvs = null;
        String[] envIds = null;
        Env env = null;
        String envList = "";

        String ownApps = null;
        String[] appIds = null;
        App app = null;
        String appList = "";

        Role role = null;
        for (int i = 0; i < users.size(); i++) {
            map = new HashMap<>();

            map.put("id", users.get(i).getId());
            map.put("name", users.get(i).getName());

            ownEnvs = users.get(i).getOwnEnvs();
            envList = "无";
            if (ownEnvs != null && ownEnvs.length() > 0) {
                envIds = ownEnvs.split(",");
                envList = "";
                for (int j = 0; j < envIds.length; j++) {
                    env = envDao.get(Long.parseLong(envIds[j]));
                    if (env == null) {
                        continue;
                    }
                    if (j == (envIds.length - 1)) {
                        envList += env.getName();
                    } else {
                        envList += env.getName() + ", ";
                    }
                }
            }
            map.put("envList", envList);

            ownApps = users.get(i).getOwnApps();
            appList = "无";
            if (ownApps != null && ownApps.length() > 0) {
                appIds = ownApps.split(",");
                appList = "";
                for (int z = 0; z < appIds.length; z++) {
                    app = appDao.get(Long.parseLong(appIds[z]));
                    if (app == null) {
                        continue;
                    }
                    if (z == (appIds.length - 1)) {
                        appList += app.getName();
                    } else {
                        appList += app.getName() + ", ";
                    }
                }
            }
            map.put("appList", appList);

            role = roleDao.get(users.get(i).getRoleId());
            if (role != null) {
                map.put("role", role.getRoleName());
            } else {
                map.put("role", "无");
            }

            list.add(map);
        }

        return list;
    }

    @Override
    public Map<String, Object> getEnvAndApps(Long userId) {
        User user = getUser(userId);

        String ownEnvs = user.getOwnEnvs();
        String ownApps = user.getOwnApps();
        String ownRoleId = String.valueOf(user.getRoleId());

        List<Env> envs = envDao.findAll();
        List<App> apps = appDao.findAll();
        List<Role> roles = roleDao.findAll();

        Env env = null;
        String envId = null;

        List<Map<String, Object>> envMaps = new ArrayList<>();
        for (int i = 0; i < envs.size(); i++) {
            env = envs.get(i);

            envId = String.valueOf(env.getId());

            Map<String, Object> tmp = new HashMap<>();

            tmp.put("envId", envId);
            tmp.put("envName", env.getName());
            if (ownEnvs == null || ownEnvs.isEmpty()) {
                tmp.put("hashAuth", false);
            } else {
                tmp.put("hashAuth", ownEnvs.contains(envId));
            }

            envMaps.add(tmp);
        }

        App app = null;
        String appId = null;

        List<Map<String, Object>> appMaps = new ArrayList<>();
        for (int j = 0; j < apps.size(); j++) {
            app = apps.get(j);

            appId = String.valueOf(app.getId());

            Map<String, Object> tmp = new HashMap<>();

            tmp.put("appId", appId);
            tmp.put("appName", app.getName());
            if (ownApps == null || ownApps.isEmpty()) {
                tmp.put("hashAuth", false);
            } else {
                tmp.put("hashAuth", ownApps.contains(appId));
            }

            appMaps.add(tmp);
        }

        Role role = null;
        String roleId = null;

        List<Map<String, Object>> roleMaps = new ArrayList<>();
        for (int z = 0; z < roles.size(); z++) {
            role = roles.get(z);

            roleId = String.valueOf(role.getId());

            Map<String, Object> tmp = new HashMap<>();

            tmp.put("roleId", role.getId());
            tmp.put("roleName", role.getRoleName());
            tmp.put("hashRole", roleId.equals(ownRoleId));

            roleMaps.add(tmp);
        }

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("envs", envMaps);
        map.put("apps", appMaps);
        map.put("roles", roleMaps);

        return map;
    }

    @Override
    public Map<String, Object> setAuth(Long userId, String envs, String apps, Integer roleId) {
        User user = userDao.get(userId);

        user.setOwnEnvs(envs);
        user.setOwnApps(apps);
        user.setRoleId(roleId);

        userDao.update(user);

        Map<String, Object> map = new HashMap<String, Object>();

        return map;
    }

}
