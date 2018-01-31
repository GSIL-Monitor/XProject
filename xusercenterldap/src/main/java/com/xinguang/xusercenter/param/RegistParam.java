package com.xinguang.xusercenter.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yangsh
 */
@Getter
@Setter
public class RegistParam {

    private String uid; // 用户名
    private String userPassword; // 密码
    private String o; // 公司Code
    private String cn; // 姓名
    private String mobile; // 手机
    private String mail; // 邮箱

}
