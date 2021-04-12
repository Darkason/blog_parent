package com.darkason.gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private static String key = "hello_world"; //秘钥

    private static long exp = 30 * 60 * 1000; //过期时间

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    /**
     * 生成JWT
     *
     * @param id
     * @param subject
     * @return
     */
    public static String createJWT(String id, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setId(id)  //唯一的ID(登录用户id)
                .setSubject(subject) // 主题,可以是JSON数据
                .setIssuedAt(now)  // 签发时间
                .signWith(SignatureAlgorithm.HS256, key) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .claim("role", "admin");  // 签发者
        if (exp > 0) {
            builder.setExpiration(new Date(nowMillis + exp));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     *
     * @param jwtStr
     * @return
     */
    public static Claims parseJWT(String jwtStr) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }

    public static void main(String[] args) {
        System.out.println(JwtUtil.createJWT("1", "admin"));
    }

}
