package com.xinguang.xusercenter.common.util;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.security.MessageDigest;

/**
 * Created by yangsh
 */
public class ValiPasswordUtil {

    /**
     * 验证密码是否匹配
     * @param ldappw LDAP 密码
     * @param password 用户密码
     * @return
     */
    public static boolean isMatch(String ldappw, String password) {
        if (ldappw == null || password == null) {
            return false;
        }

        ldappw = ldappw.substring(6);

        try {
            // 解码BASE64
            byte[] ldappwbyte = Base64.decode(ldappw);
            byte[] shacode;
            byte[] salt;

            // 前 20 位是 SHA-1 加密段, 20位后是最初加密时的随机明文
            if (ldappwbyte.length <= 20) {
                shacode = ldappwbyte;
                salt = new byte[0];
            } else {
                shacode = new byte[20];
                salt = new byte[ldappwbyte.length - 20];

                System.arraycopy(ldappwbyte, 0, shacode, 0, 20);
                System.arraycopy(ldappwbyte, 20, salt, 0, salt.length);
            }

            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // 把用户输入的密码添加到摘要计算信息
            md.update(password.getBytes());
            // 把随机明文添加到摘要计算信息
            md.update(salt);

            // 按 SSHA 把当前用户密码进行计算
            byte[] inputpwbyte = md.digest();

            return MessageDigest.isEqual(shacode, inputpwbyte);
        } catch (Exception e) {}

        return false;
    }

}
