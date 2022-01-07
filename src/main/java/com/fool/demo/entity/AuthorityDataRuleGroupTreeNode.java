package com.fool.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @author fool
 * @date 2022/1/6 9:18
 */
@Data
public class AuthorityDataRuleGroupTreeNode {

    private Integer authorityId;

    private Integer dataRuleGroupId;

    private String name;

    private List<AuthorityDataRuleGroupTreeNode> children;

}
