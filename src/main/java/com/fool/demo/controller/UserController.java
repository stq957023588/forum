package com.fool.demo.controller;

import com.fool.demo.entity.CustomizeUser;
import com.fool.demo.entity.UserDTO;
import com.fool.demo.mapstruct.UserConvertor;
import com.fool.demo.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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


}
