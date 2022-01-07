package com.fool.demo.controller;

import com.fool.demo.entity.CommonQUERY;
import com.fool.demo.entity.CustomizeUser;
import com.fool.demo.entity.UserAddDTO;
import com.fool.demo.entity.UserDTO;
import com.fool.demo.mapstruct.UserConvertor;
import com.fool.demo.service.UserService;
import com.fool.demo.utils.UserUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author fool
 * @date 2021/12/17 16:57
 */
@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "用户信息", notes = "根据token获取,token可在请求头,url参数中")
    @RequestMapping(value = "user/info", method = RequestMethod.GET)
    public UserDTO getUserInfo(String token, Principal principal) {
        log.info("Principal:" + principal);

        if (principal == null) {
            return UserUtils.getUserInfo(token);
        }

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;

        CustomizeUser customizeUser = (CustomizeUser) authenticationToken.getPrincipal();
        return UserConvertor.INSTANCE.toDataTransferObject(customizeUser);
    }

    @ApiOperation("新增用户")
    @RequestMapping(value = "user", method = RequestMethod.POST)
    public void add(@RequestBody UserAddDTO dto) {
        userService.add(dto);
    }

    @ApiOperation("用户列表")
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public PageInfo<UserDTO> getUserList(CommonQUERY query) {
        return userService.getUserList(query);
    }

}
