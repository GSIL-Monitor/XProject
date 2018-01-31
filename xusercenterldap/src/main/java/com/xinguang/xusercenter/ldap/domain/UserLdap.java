package com.xinguang.xusercenter.ldap.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yangsh on 2017-04-19
 */
@Getter
@Setter
public class UserLdap {

    private String uidNumber; // 用户ID
    private String uid; // 用户名
    private String userPassword; // 密码
    private String o; // 公司
    private String gidNumber; // 组ID
    private String cn; // 姓名
    private String sn; // 姓
    private String givenName; // 名
    private String mobile; // 手机
    private String mail; // 邮箱
    private String homeDirectory; // 用户主目录: /home/{uid}
    private String loginShell; // shell 默认: /bin/bash

}
