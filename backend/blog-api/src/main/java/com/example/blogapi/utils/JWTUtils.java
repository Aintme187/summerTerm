package com.example.blogapi.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {
    /*
    jwtToken就是密钥
     */
    private static final String jwtToken = "123456";

    public static String createToken(Long userId){
        Map<String, Object> claims = new HashMap<>();

        claims.put("userId", userId);
        /*
        jwt token 的组成部分： （Header）密钥 + 用户信息claims + 过期时间
         */
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000));
        String token = jwtBuilder.compact();
        return token;
    }

    /**
     * body部分返回的是claims
     * @param token
     * @return
     */
    public static Map<String, Object> checkToken(String token){
        try{
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
