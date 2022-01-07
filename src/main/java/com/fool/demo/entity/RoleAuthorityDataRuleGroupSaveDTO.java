package com.fool.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @author fool
 * @date 2022/1/6 11:33
 */
@Data
public class RoleAuthorityDataRuleGroupSaveDTO {
    private Integer roleId;

    private List<AuthorityDataRuleGroupTreeNode> authorityDataRuleGroups;
}
