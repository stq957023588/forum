package com.fool.demo.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/20 14:35
 */
public class CustomizeUser extends org.springframework.security.core.userdetails.User implements Nameable {

    private final String name;

    public CustomizeUser(String name, String username, String password, List<GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
