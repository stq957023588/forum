package com.fool.demo.mapstruct;

import com.fool.demo.domain.Authority;
import com.fool.demo.entity.AuthorityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author fool
 * @date 2021/12/24 10:37
 */
@Mapper
public interface AuthorityConvertor extends DomainAndDataTransferObjectConvertor<Authority, AuthorityDTO> {

    AuthorityConvertor INSTANCE = Mappers.getMapper(AuthorityConvertor.class);

    @Mapping(source = "newData.id", target = "id")
    @Mapping(source = "newData.name", target = "name")
    @Mapping(source = "newData.description", target = "description")
    @Mapping(source = "newData.url", target = "url")
    @Mapping(source = "newData.method", target = "method")
    @Mapping(source = "origin.createTime", target = "createTime")
    Authority toDomain(AuthorityDTO newData,Authority origin);

}
