package com.xinguang.xusercenter.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yangsh
 */
@Getter
@Setter
public class LoginParam {

    private String uid; // 用户名
    private String userPassword; // 密码
    private String service; // 应用地址
    private String lt; // 登录凭据

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("POST:/login");

        sb.append("?uid=").append(uid);
        sb.append("&userPassword=").append(userPassword);
        sb.append("&service=").append(service);
        sb.append("&lt=").append(lt);

        return sb.toString();
    }

}
