package com.xinguang.xusercenter.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yangsh
 */
@Getter
@Setter
public class UpdateUserInfoParam {

    private String id; // 用户ID
    private String name; // 姓名
    private String phone; // 手机
    private String email; // 邮箱

}
