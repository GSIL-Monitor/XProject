package com.xinguang.xusercenter.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangsh
 */
public class ValiUtil {

    /**
     * 验证手机号
     * @param mobile 手机号
     * @return
     */
    public static boolean isMobile(final String mobile) {
        if (mobile == null) {
            return false;
        }
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 验证邮箱
     * @param mail 邮箱
     * @return
     */
    public static boolean isMail(final String mail) {
        if (mail == null) {
            return false;
        }
        Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"); // 验证邮箱
        Matcher m = p.matcher(mail);
        return m.matches();
    }

}
