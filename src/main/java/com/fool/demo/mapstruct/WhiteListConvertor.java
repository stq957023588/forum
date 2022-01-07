package com.fool.demo.mapstruct;

import com.fool.demo.domain.WhiteList;
import com.fool.demo.entity.WhiteListAddDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author fool
 * @date 2021/12/28 9:40
 */
@Mapper
public interface WhiteListConvertor {

    WhiteListConvertor INSTANCE = Mappers.getMapper(WhiteListConvertor.class);

    WhiteList toDomain(WhiteListAddDTO dto);

}
