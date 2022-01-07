package com.fool.demo.service;

import com.fool.demo.domain.Role;
import com.fool.demo.entity.CommonQUERY;
import com.fool.demo.entity.RoleDTO;
import com.fool.demo.entity.UserRoleAddDTO;
import com.fool.demo.entity.UserRoleQUERY;
import com.fool.demo.mapper.RoleMapper;
import com.fool.demo.mapstruct.RoleConvertor;
import com.fool.demo.utils.PageUtils;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author fool
 * @date 2021/12/16 15:27
 */
@Service
public class RoleService {

    private final RoleMapper roleMapper;

    private Role superRole;

    @Value("${security.super-role}")
    public void setSuperRole(String superRole) {
        this.superRole = new Role(superRole);
    }


    public RoleService(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Cacheable(value = "is-super-role",key = "#roleId",condition = "#result")
    public boolean isSuperRole(Integer roleId){
       return Optional.ofNullable(roleId)
                .map(Integer::longValue)
                .map(roleMapper::selectByPrimaryKey)
                .map(e -> superRole.getName().equals(e.getName()))
                .orElse(false);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addUserRole(UserRoleAddDTO dto) {
        for (RoleDTO role : dto.getRoles()) {
            roleMapper.insertUserRole(dto.getUserId(), role.getId());
        }
    }

    public void add(RoleDTO role) {
        Role exist = roleMapper.selectByName(role.getName());
        if (exist != null) {
            throw new RuntimeException("the same name role exist");
        }
        Role entity = RoleConvertor.INSTANCE.toDomain(role);
        roleMapper.insertSelective(entity);

    }

    public void update(RoleDTO role) {
        Role r = roleMapper.selectByPrimaryKey(role.getId().longValue());
        Assert.notNull(r, "角色不存在");
        Assert.state(!r.getName().equals(superRole.getName()), "无法修改超级管理员信息");
        Role exist = roleMapper.selectByNameAndNotEqualsId(role.getName(), role.getId());
        if (exist != null) {
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


    public void deleteUserRole(List<Integer> userRoleIdList){
        roleMapper.deleteUserRole(userRoleIdList);
    }

    public void delete(List<Integer> roleIdList) {
        List<Role> roles = roleMapper.selectByIdList(roleIdList);
        for (Role role : roles) {
            Assert.state(!role.getName().equals(superRole.getName()), "无法删除超级管理员");
        }
        roleMapper.deleteLogicByIdList(roleIdList);
    }

    public PageInfo<RoleDTO> getUserRoles(UserRoleQUERY query) {
        ISelect select = () -> roleMapper.selectByUserId(query.getUserId());
        return PageUtils.doSelect(select, query, RoleConvertor.INSTANCE::toDataTransferObject);
    }

    public PageInfo<RoleDTO> getReverseUserRole(UserRoleQUERY query) {
        ISelect select = () -> roleMapper.selectUserDontHaveRoles(query.getUserId());
        return PageUtils.doSelect(select, query, RoleConvertor.INSTANCE::toDataTransferObject);
    }

}
