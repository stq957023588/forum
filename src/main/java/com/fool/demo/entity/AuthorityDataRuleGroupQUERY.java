package com.fool.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fool
 * @date 2022/1/4 11:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorityDataRuleGroupQUERY extends CommonQUERY{
    private Integer authorityId;
}
