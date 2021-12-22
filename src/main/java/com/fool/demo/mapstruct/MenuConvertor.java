package com.fool.demo.mapstruct;

import com.fool.demo.domain.Menu;
import com.fool.demo.domain.MenuExtra;
import com.fool.demo.entity.MenuDTO;
import com.fool.demo.entity.MenuRoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author fool
 * @date 2021/12/21 11:04
 */
@Mapper
public interface MenuConvertor extends DomainAndDataTransferObjectConvertor<Menu, MenuDTO> {

    MenuConvertor INSTANCE = Mappers.getMapper(MenuConvertor.class);

    @Mapping(source = "url", target = "path")
    MenuRoleDTO toMenuRole(MenuExtra menu);


}
