package com.xinguang.xusercenter.service;

import com.xinguang.xusercenter.common.base.Constants;
import com.xinguang.xusercenter.common.base.ReturnData;
import com.xinguang.xusercenter.common.util.ReturnUtil;
import com.xinguang.xusercenter.common.util.StringUtil;
import com.xinguang.xusercenter.entity.User;
import com.xinguang.xusercenter.mapper.UserMapper;
import com.xinguang.xusercenter.param.UpdateUserInfoParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by yangsh
 */
@Service
@Slf4j
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 用户信息修改
     * @param updateUserInfoParam
     * @return
     */
    public ReturnData updateUserInfo(UpdateUserInfoParam updateUserInfoParam, HttpSession session) {
        User user = userMapper.getUserById(updateUserInfoParam.getId());

        boolean result = isModify(user, updateUserInfoParam);

        if (result) {
            user.setName(updateUserInfoParam.getName());
            user.setPhone(updateUserInfoParam.getPhone());
            user.setEmail(updateUserInfoParam.getEmail());

            userMapper.updateUser(user);

            session.setAttribute(Constants.SESSION_USER_KEY, user);
            log.info("user info update success");
        } else {
            log.info("user info nothing update");
        }

        return ReturnUtil.success();
    }

    private boolean isModify(User user, UpdateUserInfoParam updateUserInfoParam) {
        String oldName = user.getName();
        String oldPhone = user.getPhone();
        String oldEmail = user.getEmail();

        String newName = updateUserInfoParam.getName();
        String newPhone = updateUserInfoParam.getPhone();
        String newEmail = updateUserInfoParam.getEmail();

        boolean result = false;
        if (!StringUtil.isEquals(oldName, newName)) {
            log.info("name: " + oldName + " to " + newName);
            result = true;
        }

        if (!StringUtil.isEquals(oldPhone, newPhone)) {
            log.info("phone: " + oldPhone + " to " + newPhone);
            result = true;
        }

        if (!StringUtil.isEquals(oldEmail, newEmail)) {
            log.info("email: " + oldEmail + " to " + newEmail);
            result = true;
        }

        return result;
    }

}
