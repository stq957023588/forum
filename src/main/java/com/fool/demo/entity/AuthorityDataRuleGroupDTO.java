package com.fool.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @author fool
 * @date 2022/1/5 13:35
 */
@Data
public class AuthorityDataRuleGroupDTO {
    private Integer authorityId;

    private List<DataRuleGroupDTO> dataRuleGroups;

}
