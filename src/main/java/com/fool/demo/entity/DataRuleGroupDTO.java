package com.fool.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @author fool
 * @date 2022/1/4 11:06
 */
@Data
public class DataRuleGroupDTO {

    private Integer id;

    private String name;

    private List<DataRuleDTO> dataRules;

}
