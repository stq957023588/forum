package com.fool.demo.service;

import com.fool.demo.domain.Role;
import com.fool.demo.entity.RoleDTO;
import com.fool.demo.mapper.RoleMapper;
import com.fool.demo.mapstruct.RoleConvertor;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

/**
 * @author fool
 * @date 2021/12/16 15:27
 */
@Service
public class RoleService {

    private final RoleMapper roleMapper;

    public RoleService(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public void addRole(RoleDTO role) {

        Role entity = RoleConvertor.INSTANCE.toDomain(role);

        roleMapper.insertSelective(entity);

    }

    public void update(RoleDTO role) {

        Role r = roleMapper.selectByName(role.getName());
        if (r != null) {
            throw new RuntimeException("the same name role exist");
        }
        Role origin = roleMapper.selectByPrimaryKey(role.getId().longValue());
        Role entity = RoleConvertor.INSTANCE.toDomain(role, origin);

        roleMapper.updateByPrimaryKey(entity);
    }

    public void getPageable(){
        ISelect select = roleMapper::selectAll;
        PageInfo<Role> rolePageInfo = PageHelper.startPage(1, 10).doSelectPageInfo(select);

    }

}
