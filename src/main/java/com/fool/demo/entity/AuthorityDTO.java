package com.fool.demo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author fool
 * @date 2021/12/24 10:34
 */
@Data
public class AuthorityDTO {

    private Integer id ;

    private String name;

    private String url;

    private String description;

    private String method;

    private Date createTime;

}
