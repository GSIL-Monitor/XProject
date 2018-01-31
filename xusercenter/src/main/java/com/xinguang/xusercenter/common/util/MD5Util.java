package com.xinguang.xusercenter.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	/**
     * MD5 加密
     * @param srcStr 加密内容
     * @return
     */
    public static String getMD5(String srcStr) {
        byte[] in = srcStr.getBytes();
        char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
 
        String result = "";
 
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); // 获得一个 MD5 摘要算法的对象
 
            md.update(in);
 
            byte[] tmp = md.digest(); // 获得 MD5 的摘要结果
            char[] str = new char[32];
 
            byte b =  0;
 
            for (int i =  0; i < 16; i++) {
                b = tmp[i];
                str[ 2*i] = hexChar[b>>> 4 & 0xf]; // 取每一个字节的低四位换成 16 进制字母
                str[ 2*i+ 1] = hexChar[b & 0xf]; // 取每一个字节的高四位换成 16 进制字母
            }
 
            result = new String(str);
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
 
        return result;
    }
 
    /**
     * MD5 超级加密
     * @param srcStr 加密内容
     * @param salt 加密盐
     * @return
     */
    public static String getMD5WithSalt(String srcStr, String salt) {
        String[] symbols = {"$", "-", "&", "@", "~", ":", "(", "^", "|", "%", "#", "{", ")", "+"};
 
        StringBuffer sb = new StringBuffer("");
        for (int i =  0; i < symbols.length; i++) {
            sb.append(symbols[i]);
            sb.append(salt);
            sb.append(srcStr);
        }
            
        return getMD5(sb.toString());
    }

}
