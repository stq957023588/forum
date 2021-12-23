package com.fool.demo.mapstruct;

import com.fool.demo.domain.Menu;
import com.fool.demo.domain.MenuExtra;
import com.fool.demo.entity.MenuDTO;
import com.fool.demo.entity.MenuRoleDTO;
import com.fool.demo.entity.MenuTreeNode;
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

    MenuTreeNode toTreeNode(Menu menu);

    @Mapping(source = "origin.id",target = "id")
    @Mapping(source = "newData.name",target = "name")
    @Mapping(source = "newData.description",target = "description")
    @Mapping(source = "newData.url",target = "url")
    @Mapping(source = "newData.component",target = "component")
    @Mapping(source = "newData.icon",target = "icon")
    @Mapping(source = "newData.parentMenuId",target = "parentMenuId")
    @Mapping(source = "origin.enable",target = "enable")
    @Mapping(source = "origin.creator",target = "creator")
    @Mapping(source = "origin.createTime",target = "createTime")
    @Mapping(source = "origin.deleted",target = "deleted")
    @Mapping(source = "origin.deleter",target = "deleter")
    @Mapping(source = "origin.deleteTime",target = "deleteTime")
    Menu toDomain(MenuDTO newData,Menu origin);

}
