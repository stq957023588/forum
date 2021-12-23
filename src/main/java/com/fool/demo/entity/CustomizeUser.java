package com.fool.demo.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/20 14:35
 */
public class CustomizeUser extends User {
    private final Integer id;

    private final String name;

    public CustomizeUser(Integer id,String name, String username, String password, List<GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.name = name;
        this.id  = id   ;
    }

    public String getName() {
        return this.name;
    }

    public Integer getId   (){
        return this.id;
    }
}
