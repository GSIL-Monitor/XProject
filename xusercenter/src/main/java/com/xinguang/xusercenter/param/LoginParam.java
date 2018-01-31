package com.xinguang.xusercenter.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yangsh
 */
@Getter
@Setter
public class LoginParam {

    private String username; // 用户名
    private String password; // 密码
    private String service; // 应用地址
    private String lt; // 登录凭据

}
