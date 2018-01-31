package com.xinguang.xusercenter.service;

import com.xinguang.xusercenter.common.base.*;
import com.xinguang.xusercenter.common.store.LoginTicketStore;
import com.xinguang.xusercenter.common.store.ServiceTicketStore;
import com.xinguang.xusercenter.common.util.*;
import com.xinguang.xusercenter.entity.Log;
import com.xinguang.xusercenter.entity.Security;
import com.xinguang.xusercenter.entity.UserId;
import com.xinguang.xusercenter.ldap.dao.CompanyDao;
import com.xinguang.xusercenter.ldap.dao.GroupDao;
import com.xinguang.xusercenter.ldap.dao.UserDao;
import com.xinguang.xusercenter.ldap.domain.CompanyLdap;
import com.xinguang.xusercenter.ldap.domain.GroupLdap;
import com.xinguang.xusercenter.ldap.domain.UserLdap;
import com.xinguang.xusercenter.mapper.LogMapper;
import com.xinguang.xusercenter.mapper.SecurityMapper;
import com.xinguang.xusercenter.mapper.UserIdMapper;
import com.xinguang.xusercenter.param.LoginParam;
import com.xinguang.xusercenter.param.RegistParam;
import com.xinguang.xusercenter.param.UpdatePasswordParam;
import com.xinguang.xusercenter.param.UpdateUserInfoParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by yangsh
 */
@Slf4j
@Service
public class LdapService {

    @Resource
    private CompanyDao companyDao;
    @Resource
    private UserDao userDao;
    @Resource
    private GroupDao groupDao;
    @Resource
    private UserIdMapper userIdMapper;
    @Resource
    private LogMapper logMapper;
    @Resource
    private SecurityMapper securityMapper;

    /**
     * 获取公司列表 (LDAP)
     * @return
     */
    public List<CompanyLdap> getCompanyLdapList() {
        return companyDao.search("", "(&(objectclass=organization)(!(o=manager)))");
    }

    /**
     * 获取公司 (LDAP)
     * @return
     */
    public CompanyLdap getCompanyLdap(String o) {
        String companyDN = "o=" + o;
        return companyDao.find(companyDN);
    }

    /**
     * 获取组列表 (LDAP)
     * @return
     */
    public List<GroupLdap> getGroupLdapList(String o) {
        List<GroupLdap> groupLdapList = groupDao.search("o=" + o, "(objectclass=posixGroup)");
        return groupLdapList;
    }

    /**
     * 获取用户所属组列表 (LDAP)
     * @return
     */
    public List<GroupLdap> getUserGroupLdapList(String o, String uid) {
        List<GroupLdap> userGroupLdapList = groupDao.search("o=" + o, "(&(objectclass=posixGroup)(memberUid=" + uid + "))");
        return userGroupLdapList;
    }

    /**
     * 用户注册 (LDAP)
     * @param registParam
     * @return
     */
    @Transactional
    public ReturnData regist(final RegistParam registParam) {
        String uid = registParam.getUid(); // 用户名
        String userPassword = registParam.getUserPassword(); // 密码
        String o = registParam.getO(); // 公司Code
        String cn = registParam.getCn(); // 姓名
        String mobile = registParam.getMobile(); // 手机
        String mail = registParam.getMail(); // 邮箱

        // 非空验证
        if (StringUtil.isEmpty(uid) || StringUtil.isEmpty(userPassword) || StringUtil.isEmpty(o) || StringUtil.isEmpty(cn) || StringUtil.isEmpty(mobile) || StringUtil.isEmpty(mail)) {
            return ReturnUtil.fail(ReturnCode.PARAM_INCOMPLETE);
        }

        if (!ValiUtil.isMobile(mobile)) {
            return ReturnUtil.fail(ReturnCode.MOBILE_FORMAT_ERROR);
        }

        if (!ValiUtil.isMail(mail)) {
            return ReturnUtil.fail(ReturnCode.MAIL_FORMAT_ERROR);
        }

        uid = uid.trim();
        userPassword = userPassword.trim();

        List<UserLdap> uls = userDao.search("", "(&(objectclass=inetOrgPerson)(uid=" + uid + "))");
        if (!uls.isEmpty()) {
            log.info("user [uid={}] has exist", uid);
            return ReturnUtil.fail(ReturnCode.UID_HAS_EXIST);
        }

        String sn = cn.substring(0, 1); // 姓
        String givenName = cn.substring(1); // 名

        CompanyLdap companyLdap = companyDao.find("o=" + o);
        if (companyLdap == null) {
            log.error("company [o={}] not exist", o);
            return ReturnUtil.fail(ReturnCode.COMPANY_NOT_EXIST);
        }

        String dc = companyLdap.getDc(); // 公司ID

        Date now = new Date(); // 当前时间

        UserId userId = userIdMapper.get(o);

        if (userId == null) {
            userId = new UserId();

            userId.setId(String.valueOf(now.getTime()));
            userId.setUserId(Integer.valueOf(dc + "00000"));
            userId.setCompanyCode(o);
            userId.setUpdateTime(now);

            userIdMapper.add(userId);
        } else {
            userId.setUserId(userId.getUserId() + 1);
            userId.setUpdateTime(now);

            userIdMapper.update(userId);
        }

        String uidNumber = String.valueOf(userId.getUserId());

        UserLdap ul = new UserLdap();

        ul.setUidNumber(uidNumber);
        ul.setUid(uid);
        ul.setUserPassword(LdapPasswordUtil.getSSHA(userPassword));
        ul.setO(o);
        ul.setGidNumber(dc + "000");
        ul.setCn(cn);
        ul.setSn(sn);
        ul.setGivenName(givenName);
        ul.setMobile(mobile);
        ul.setMail(mail);
        ul.setHomeDirectory("/home/" + uid);
        ul.setLoginShell("/bin/bash");

        userDao.add("uid=" + uid + ",ou=People,o=" + o, ul);

        // 新增日志
        this.addLog(uidNumber, uid, ActionType.REGIST, now);

        // 新增安全
        Security security = new Security();

        security.setId(uidNumber);
        security.setCreateTime(now);

        securityMapper.addSecurity(security);

        log.info("user [uid={}] regist success", uid);

        return ReturnUtil.success();
    }

    /**
     * 用户登录 (LDAP)
     * @param loginParam
     * @param session
     * @return
     */
    public ReturnData login(final LoginParam loginParam, HttpSession session) {
        String lt = loginParam.getLt();
        boolean flag = LoginTicketStore.isExist(lt);
        if (!flag) {
            log.error("validate {} fail", lt);
            return ReturnUtil.fail(ReturnCode.LT_ERROR);
        }
        log.info("validate {} success", lt);
        LoginTicketStore.remove(lt);

        String uid = loginParam.getUid();
        String userPassword = loginParam.getUserPassword();

        // 非空验证
        if (StringUtil.isEmpty(uid) || StringUtil.isEmpty(userPassword)) {
            return ReturnUtil.fail(ReturnCode.PARAM_INCOMPLETE, LoginTicketStore.getMap());
        }

        String service = loginParam.getService();

        List<UserLdap> uls = userDao.search("", "(&(objectclass=inetOrgPerson)(uid=" + uid + "))");
        if (uls.isEmpty()) {
            return ReturnUtil.fail(ReturnCode.UNAME_ERROR, LoginTicketStore.getMap());
        }

        UserLdap ul = uls.get(0);

        String ldappw = ul.getUserPassword();
        if (!ValiPasswordUtil.isMatch(ldappw, userPassword)) {
            return ReturnUtil.fail(ReturnCode.PASSWD_ERROR, LoginTicketStore.getMap());
        }

        UsernamePasswordToken token = new UsernamePasswordToken(uid, userPassword);
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            log.info("user [uid=" + uid + "] login fail: username error");
            return ReturnUtil.fail(ReturnCode.UNAME_ERROR, LoginTicketStore.getMap());
        } catch (IncorrectCredentialsException e) {
            log.info("user [uid=" + uid + "] login fail: password error");
            return ReturnUtil.fail(ReturnCode.PASSWD_ERROR, LoginTicketStore.getMap());
        } catch (AuthenticationException e) {
            log.error("user [uid=" + uid + "] login fail: other error");
            return ReturnUtil.fail(ReturnCode.OTHER_ERROR, LoginTicketStore.getMap());
        }

        if (subject.isAuthenticated()) {
            // 认证成功
            log.info("user [uid={}] login success", uid);

            String uidNumber = ul.getUidNumber();

            String lastLoginTime = logMapper.getLastLoginTimeByUserId(uidNumber);
            if (StringUtil.isEmpty(lastLoginTime)) {
                lastLoginTime = "此前无登录信息";
            }

            // 新增日志
            this.addLog(uidNumber, uid, ActionType.LOGIN, new Date());

            Integer loginCount = logMapper.getLoginCountByUserId(uidNumber);

            log.info("add user [uid={}] to session", uid);
            session.setAttribute(Constants.SESSION_USER_KEY, ul);
            session.setAttribute(Constants.SESSION_LAST_LOGIN_TIME, lastLoginTime);
            session.setAttribute(Constants.SESSION_LOGIN_COUNT, loginCount);

            if (StringUtil.isNotEmpty(service)) {
                String ticket = TicketUtil.getServiceTicket();
                ServiceTicket st = new ServiceTicket(ticket, service, ul);
                log.info("add {} to memory", ticket);
                ServiceTicketStore.add(ticket, st);

                Map<String, Object> data = new HashMap<>();

                data.put("ticket", ticket);

                return ReturnUtil.success(data);
            } else {
                return ReturnUtil.success();
            }
        } else {
            // 认证失败
            log.error("user [uid={}] login fail: unknown error", uid);
            return ReturnUtil.fail(ReturnCode.UNKNOWN_ERROR, LoginTicketStore.getMap());
        }
    }

    /**
     * 修改用户密码 (LDAP)
     * @param updatePasswordParam
     * @return
     */
    public ReturnData updatePassword(final UpdatePasswordParam updatePasswordParam) {
        String uid = updatePasswordParam.getUid();
        String o = updatePasswordParam.getO();
        String oldPassword = updatePasswordParam.getOpassword();
        String newPassword = updatePasswordParam.getNpassword();

        // 非空验证
        if (StringUtil.isEmpty(uid) || StringUtil.isEmpty(o) || StringUtil.isEmpty(oldPassword) || StringUtil.isEmpty(newPassword)) {
            return ReturnUtil.fail(ReturnCode.PARAM_INCOMPLETE);
        }

        String userDN = "uid=" + uid + ",ou=People,o=" + o;

        log.info("dn=[" + userDN + "]");

        UserLdap ul = userDao.find(userDN);

        String ldappw = ul.getUserPassword();

        if (!ValiPasswordUtil.isMatch(ldappw, oldPassword)) {
            return ReturnUtil.fail(ReturnCode.PASSWD_ERROR);
        }

        Map<String, Object> param = new HashMap<>();

        param.put("userPassword", LdapPasswordUtil.getSSHA(newPassword));

        userDao.modfiyAttrs(userDN, param);

        // 新增日志
        this.addLog(ul.getUidNumber(), uid, ActionType.UPDATEPASSWORD, new Date());

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
        } catch (SessionException ise) {
            log.debug("Encountered session exception during logout. This can generally safely be ignored.", ise);
        }

        log.info("user [uid=" + uid + "] password update success");

        return ReturnUtil.success();
    }

    /**
     * 修改用户信息 (LDAP)
     * @param updateUserInfoParam
     * @param session
     * @return
     */
    public ReturnData updateUserInfo(UpdateUserInfoParam updateUserInfoParam, HttpSession session) {
        String uid = updateUserInfoParam.getUid();
        String o = updateUserInfoParam.getO();
        String cn = updateUserInfoParam.getCn();
        String mobile = updateUserInfoParam.getMobile();
        String mail = updateUserInfoParam.getMail();

        // 非空验证
        if (StringUtil.isEmpty(uid) || StringUtil.isEmpty(o) || StringUtil.isEmpty(cn) || StringUtil.isEmpty(mobile) || StringUtil.isEmpty(mail)) {
            return ReturnUtil.fail(ReturnCode.PARAM_INCOMPLETE);
        }

        if (!ValiUtil.isMobile(mobile)) {
            return ReturnUtil.fail(ReturnCode.MOBILE_FORMAT_ERROR);
        }

        if (!ValiUtil.isMail(mail)) {
            return ReturnUtil.fail(ReturnCode.MAIL_FORMAT_ERROR);
        }

        String userDN = "uid=" + uid + ",ou=People,o=" + o;

        log.info("dn=[" + userDN + "]");

        UserLdap ul = userDao.find(userDN);

        Map<String, Object> param = new HashMap<>();

        if (!cn.equals(ul.getCn())) {
            log.info("cn: " + ul.getCn() + " to " + cn);

            String sn = cn.substring(0, 1); // 姓
            String givenName = cn.substring(1); // 名

            ul.setCn(cn);
            ul.setSn(sn);
            ul.setGivenName(givenName);

            param.put("cn", cn);
            param.put("sn", sn);
            param.put("givenName", givenName);
        }

        if (!mobile.equals(ul.getMobile())) {
            log.info("mobile: " + ul.getMobile() + " to " + mobile);

            ul.setMobile(mobile);
            param.put("mobile", mobile);
        }

        if (!mail.equals(ul.getMail())) {
            log.info("mail: " + ul.getMail() + " to " + mail);

            ul.setMail(mail);
            param.put("mail", mail);
        }

        if (!param.isEmpty()) {
            userDao.modfiyAttrs(userDN, param);

            session.setAttribute(Constants.SESSION_USER_KEY, ul);

            log.info("user [uid=" + uid + "] info update success");
        } else {
            log.info("user [uid=" + uid + "] info nothing update");
        }

        // 新增日志
        this.addLog(ul.getUidNumber(), uid, ActionType.UPDATEINFO, new Date());

        return ReturnUtil.success();
    }

    private void addLog(String uidNumber, String uid, ActionType actionType, Date now) {
        Log logx = new Log();

        logx.setId(UUID.randomUUID().toString());
        logx.setUserId(uidNumber);
        logx.setUsername(uid);
        logx.setActionType(actionType);
        logx.setCreateTime(now);

        logMapper.add(logx);
    }

}
