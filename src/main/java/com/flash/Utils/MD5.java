package com.flash.Utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

public class MD5 {

    /**
     * 加密字符串
     * @param str
     * @return
     * @throws Exception
     */
    public static String encodeByMD5(String str) throws Exception{
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        String newStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }
}
