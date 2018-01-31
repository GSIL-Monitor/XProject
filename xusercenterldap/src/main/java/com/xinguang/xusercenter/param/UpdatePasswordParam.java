package com.xinguang.xusercenter.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yangsh
 */
@Getter
@Setter
public class UpdatePasswordParam {

    private String uid; // 用户名
    private String o; // 公司Code
    private String opassword; // 原密码
    private String npassword; // 新密码

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("POST:/updatePassword");

        sb.append("?uid=").append(uid);
        sb.append("&o=").append(o);

        return sb.toString();
    }

}
