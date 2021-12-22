package com.fool.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/20 13:55
 */
@Data
public class UserDTO {

    private String name;

    private String email;

    private List<String> roles;
}
