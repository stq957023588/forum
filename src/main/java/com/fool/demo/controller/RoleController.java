package com.fool.demo.controller;

import com.fool.demo.entity.CommonQUERY;
import com.fool.demo.entity.RoleDTO;
import com.fool.demo.service.RoleService;
import com.github.pagehelper.PageInfo;
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

    @RequestMapping(value = "role", method = RequestMethod.GET)
    public PageInfo<RoleDTO> getRoles(CommonQUERY query) {
        return roleService.getRoles(query);
    }

    @RequestMapping(value = "role", method = RequestMethod.POST)
    public void add(@RequestBody RoleDTO role) {
        roleService.add(role);
    }

    @RequestMapping(value = "role", method = RequestMethod.PUT)
    public void update(@RequestBody RoleDTO role) {
        roleService.update(role);
    }


    @RequestMapping(value = "role", method = RequestMethod.DELETE)
    public void delete(@RequestBody List<Integer> roleIdList) {
        roleService.delete(roleIdList);
    }
}
