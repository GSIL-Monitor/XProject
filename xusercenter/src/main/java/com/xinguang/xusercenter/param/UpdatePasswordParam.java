package com.xinguang.xusercenter.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yangsh
 */
@Getter
@Setter
public class UpdatePasswordParam {

    private String username; // 用户名
    private String opassword; // 原密码
    private String npassword; // 新密码

}
