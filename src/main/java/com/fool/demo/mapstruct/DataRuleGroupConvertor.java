package com.fool.demo.mapstruct;

import com.fool.demo.domain.AuthorityAndDataRuleGroup;
import com.fool.demo.domain.DataRuleGroup;
import com.fool.demo.entity.AuthorityDataRuleGroupTreeNode;
import com.fool.demo.entity.DataRuleGroupDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author fool
 * @date 2022/1/4 11:17
 */
@Mapper
public interface DataRuleGroupConvertor extends DomainAndDataTransferObjectConvertor<DataRuleGroup, DataRuleGroupDTO> {
    DataRuleGroupConvertor INSTANCE = Mappers.getMapper(DataRuleGroupConvertor.class);


    @Mapping(source = "dataRuleGroupName", target = "name")
    AuthorityDataRuleGroupTreeNode toTreeNode(AuthorityAndDataRuleGroup authorityAndDataRuleGroup);
}
