package com.fool.demo.controller;

import com.fool.demo.entity.CommonQUERY;
import com.fool.demo.entity.RoleDTO;
import com.fool.demo.entity.UserRoleAddDTO;
import com.fool.demo.entity.UserRoleQUERY;
import com.fool.demo.service.RoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/23 17:01
 */
@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "角色列表", notes = "分页查询角色列表")
    @RequestMapping(value = "role", method = RequestMethod.GET)
    public PageInfo<RoleDTO> getRoles(CommonQUERY query) {
        return roleService.getRoles(query);
    }

    @ApiOperation(value = "添加角色")
    @RequestMapping(value = "role", method = RequestMethod.POST)
    public void add(@RequestBody RoleDTO role) {
        roleService.add(role);
    }

    @ApiOperation("修改角色")
    @RequestMapping(value = "role", method = RequestMethod.PUT)
    public void update(@RequestBody RoleDTO role) {
        roleService.update(role);
    }


    @ApiOperation(value = "删除角色", notes = "逻辑删除角色")
    @RequestMapping(value = "role", method = RequestMethod.DELETE)
    public void delete(@RequestBody List<Integer> roleIdList) {
        roleService.delete(roleIdList);
    }

    @ApiOperation(value = "用户角色", notes = "根据用户查询角色")
    @RequestMapping(value = "user-role", method = RequestMethod.GET)
    public PageInfo<RoleDTO> getUserRole(UserRoleQUERY query) {
        return roleService.getUserRoles(query);
    }

    @ApiOperation(value = "添加用户角色", notes = "支持为同一个用户添加多个角色,不支持为多个用户添加角色")
    @RequestMapping(value = "user-role", method = RequestMethod.POST)
    public void addUserRole(@RequestBody UserRoleAddDTO dto) {
        roleService.addUserRole(dto);
    }

    @ApiOperation(value = "删除用户角色", notes = "支持批量删除")
    @RequestMapping(value = "user-role", method = RequestMethod.DELETE)
    public void deleteUserRole(@RequestBody List<Integer> userRoleIdList) {
        roleService.deleteUserRole(userRoleIdList);
    }


    @ApiOperation(value = "用户未拥有角色", notes = "根据用户查询角色")
    @RequestMapping(value = "reverse-user-role", method = RequestMethod.GET)
    public PageInfo<RoleDTO> getReverseUserRole(UserRoleQUERY query) {
        return roleService.getReverseUserRole(query);
    }
}
