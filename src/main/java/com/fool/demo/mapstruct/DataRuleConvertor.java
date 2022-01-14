package com.fool.demo.mapstruct;

import com.fool.demo.domain.DataRule;
import com.fool.demo.entity.DataRuleDTO;
import com.fool.demo.utils.ExtraCondition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author fool
 * @date 2022/1/4 10:26
 */
@Mapper
public interface DataRuleConvertor extends DomainAndDataTransferObjectConvertor<DataRule, DataRuleDTO> {
    DataRuleConvertor INSTANCE = Mappers.getMapper(DataRuleConvertor.class);

    @Mapping(source = "field", target = "key")
    @Mapping(source = "condition", target = "operator")
    @Mapping(source = "value", target = "value")
    ExtraCondition toExtraCondition(DataRuleDTO dataRule);


}
