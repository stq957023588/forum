package com.fool.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fool
 * @date 2022/1/4 10:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataRuleGroupMemberQUERY extends CommonQUERY {
    private Integer dataRuleGroupId;
}
