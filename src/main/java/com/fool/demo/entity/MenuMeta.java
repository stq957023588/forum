package com.fool.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/20 16:41
 */
@Data
public class MenuMeta {

    private List<String> roles;

    private String title;

    private String icon;

}
