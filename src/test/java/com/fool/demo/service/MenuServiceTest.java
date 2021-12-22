package com.fool.demo.service;

import com.fool.demo.entity.MenuRoleDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/21 9:58
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MenuServiceTest {

    private MenuService menuService;

    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @Test
    void getMenu() {
        List<MenuRoleDTO> menu = menuService.getRoleMenus();

        menu.forEach(System.out::println);
    }

}
