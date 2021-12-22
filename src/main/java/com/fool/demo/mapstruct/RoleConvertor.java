package com.fool.demo.mapstruct;

import com.fool.demo.domain.Role;
import com.fool.demo.entity.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author fool
 * @date 2021/12/16 16:01
 */
@Mapper
public interface RoleConvertor extends DomainAndDataTransferObjectConvertor<Role, RoleDTO> {

    RoleConvertor INSTANCE = Mappers.getMapper(RoleConvertor.class);


    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.name", target = "name")
    @Mapping(source = "dto.description", target = "description")
    @Mapping(source = "origin.createTime", target = "createTime")
    Role toDomain(RoleDTO dto, Role origin);
}
