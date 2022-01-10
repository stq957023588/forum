package com.fool.demo.mapstruct;

import com.fool.demo.domain.DictionaryType;
import com.fool.demo.entity.DictTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author fool
 * @date 2022/1/7 10:42
 */
@Mapper
public interface DictTypeConvertor extends DomainAndDataTransferObjectConvertor<DictionaryType, DictTypeDTO> {
    DictTypeConvertor INSTANCE = Mappers.getMapper(DictTypeConvertor.class);

}
