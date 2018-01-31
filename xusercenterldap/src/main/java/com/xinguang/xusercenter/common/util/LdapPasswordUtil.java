package com.xinguang.xusercenter.common.util;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by yangsh
 */
public class LdapPasswordUtil {

    public static String getSSHA(final String password) {
        byte[] salt = getSalt().getBytes();

        String base64SSHAPassword = null;

        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-1");

            md.update(password.getBytes());
            md.update(salt);

            byte[] bs = md.digest();

            byte[] all = new byte[bs.length+salt.length];
            System.arraycopy(bs,0, all,0, bs.length);
            System.arraycopy(salt,0, all, bs.length, salt.length);

            base64SSHAPassword = "{SSHA}" + Base64.encode(all);
        } catch (Exception e) {}

        return base64SSHAPassword;
    }

    private static String getSalt() {
        char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        Random random = new Random();

        StringBuffer sb = new StringBuffer("");
        for (int i = 1; i <= 8; i++) {
            sb.append(hexChar[random.nextInt(16)]);
        }

        return sb.toString();
    }
}
