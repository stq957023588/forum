package com.fool.demo.mapstruct;

import com.fool.demo.domain.DataRule;
import com.fool.demo.entity.DataRuleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author fool
 * @date 2022/1/4 10:26
 */
@Mapper
public interface DataRuleConvertor extends DomainAndDataTransferObjectConvertor<DataRule, DataRuleDTO> {
    DataRuleConvertor INSTANCE = Mappers.getMapper(DataRuleConvertor.class);



}
