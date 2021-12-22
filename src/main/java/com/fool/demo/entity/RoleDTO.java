package com.fool.demo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author fool
 * @date 2021/12/16 15:49
 */
@Data
public class RoleDTO {

    private Integer id;

    private String name;

    private String description;

    private Date createTime;

}
