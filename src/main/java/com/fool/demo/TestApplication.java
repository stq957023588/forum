package com.fool.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author fool
 * @date 2021/10/21 14:30
 */
public class TestApplication {

    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
        Algorithm algorithm = Algorithm.HMAC256("secret");

        long current = System.currentTimeMillis();

        String token = JWT.create()
                .withIssuer("Fool")
                .withIssuedAt(new Date(current))
                .withExpiresAt(new Date(current + 10 * 1000))
                .withClaim("name", "wuyuwei") // 插入数据
                .sign(algorithm);
        System.out.println(token);

        // Thread.sleep(1000 * 11);

        JWTVerifier verifier = JWT.require(algorithm).withIssuer("Fool").build();
        DecodedJWT verify = verifier.verify("1231412.12124.553432");
        System.out.println(verify);
    }
}
