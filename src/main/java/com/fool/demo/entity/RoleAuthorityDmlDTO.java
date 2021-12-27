package com.fool.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/27 9:36
 */
@Data
public class RoleAuthorityDmlDTO {
    private Integer roleId;

    private List<AuthorityDTO> authorities;
}
