package com.fool.demo.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author fool
 * @date 2021/12/20 13:55
 */
@Data
public class UserDTO {

    private String id;

    private String name;

    private String email;

    private String avatar;

    private Date createTime;

    private List<String> roles;
}
