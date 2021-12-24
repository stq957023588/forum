package com.fool.demo.service;

import com.fool.demo.domain.Role;
import com.fool.demo.entity.CommonQUERY;
import com.fool.demo.entity.RoleDTO;
import com.fool.demo.mapper.RoleMapper;
import com.fool.demo.mapstruct.RoleConvertor;
import com.fool.demo.utils.PageUtils;
import com.github.pagehelper.ISelect;
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

    public void add(RoleDTO role) {
        Role entity = RoleConvertor.INSTANCE.toDomain(role);
        roleMapper.insertSelective(entity);

    }

    public void update(RoleDTO role) {

        Role r = roleMapper.selectByNameAndNotEqualsId(role.getName(), role.getId());
        if (r != null) {
            throw new RuntimeException("the same name role exist");
        }
        Role origin = roleMapper.selectByPrimaryKey(role.getId().longValue());
        Role entity = RoleConvertor.INSTANCE.toDomain(role, origin);

        roleMapper.updateByPrimaryKey(entity);
    }

    public PageInfo<RoleDTO> getRoles(CommonQUERY query) {
        ISelect select = roleMapper::selectAll;
        return PageUtils.doSelect(select, query);
    }

}
