package com.darkason.admin.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

//Shiro工具类
public class ShiroUtil {

    //加密算法
    public final static String hashAlgorithmName = "SHA-256";

    //循环次数
    public final static int hashIterations = 16;

    public static String sha256(String password, String salt) {
        return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
    }

    public static void main(String[] args) {
        String password = "123456";
        System.out.println(ShiroUtil.sha256(password, "ABC123"));
    }

}
