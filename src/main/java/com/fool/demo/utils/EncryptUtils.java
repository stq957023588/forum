package com.fool.demo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author fool
 * @date 2021/12/16 9:20
 */
public class EncryptUtils {

    public static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();


    public static void main(String[] args) {
        String encode = PASSWORD_ENCODER.encode("123456");
        System.out.println(encode);
    }



}
