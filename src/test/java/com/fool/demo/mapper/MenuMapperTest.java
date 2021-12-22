package com.fool.demo.mapper;
import com.fool.demo.domain.Menu;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author fool
 * @date 2021/12/21 9:40
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MenuMapperTest {

    private MenuMapper menuMapper;

    @Autowired
    public void setMenuMapper(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Test
    void selectAllWithRole() {
    }

    @Test
    void insert() {
        Menu menu = new Menu();
        menu.setName("TestMenu-2");
        menu.setDescription("");
        menu.setUrl("/san");
        menu.setComponent("/src/qqq");
        menu.setParentMenuId(1);
        menu.setCreator(1);

        menuMapper.insertSelective(menu);
    }
}
