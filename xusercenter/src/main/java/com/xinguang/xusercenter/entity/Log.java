package com.xinguang.xusercenter.entity;

import com.xinguang.xusercenter.common.base.ActionType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 日志
 *
 * Created by yangsh
 */
@Getter
@Setter
public class Log {

    private String id; // 日志ID
    private String userId; // 用户ID
    private String username; // 用户名
    private ActionType actionType; // 行为类型: REGIST-注册; LOGIN-登录; UPDATEPASSWORD-修改密码; LOGOUT-退出; RESETPASSWORD-重置密码
    private Date createTime; // 创建时间

}
