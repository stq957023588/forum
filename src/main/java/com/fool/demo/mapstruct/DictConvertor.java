package com.fool.demo.mapstruct;

import com.fool.demo.domain.Dictionary;
import com.fool.demo.entity.DictDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author fool
 * @date 2022/1/7 10:41
 */
@Mapper
public interface DictConvertor extends DomainAndDataTransferObjectConvertor<Dictionary, DictDTO> {
    DictConvertor INSTANCE = Mappers.getMapper(DictConvertor.class);

}
