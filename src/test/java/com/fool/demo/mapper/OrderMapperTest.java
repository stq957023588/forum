package com.fool.demo.mapper;

import com.fool.demo.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author fool
 * @date 2021/10/14 16:08
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderMapperTest {

    OrderMapper orderMapper;

    @Autowired
    private void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Test
    void insertSelective() {
        Order order = new Order();

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            order.setValue(random.nextInt(100));
            orderMapper.insertSelective(order);
        }

    }

    @Test
    void selectTest() {
        long l1 = System.currentTimeMillis();

        orderMapper.selectAll();

        long l2 = System.currentTimeMillis();

        orderMapper.selectByValue(11);
        long l3 = System.currentTimeMillis();

        log.info("Null :{}", l2 - l1);
        log.info("NonNull :{}", l3 - l2);
    }

    @Test
    void selectBetween() {
        List<Order> orders = orderMapper.selectBetween(59, 80);
        orders.forEach(System.out::println);
    }

    @Test
    void selectGreater() {
        orderMapper.selectGreater(50).forEach(System.out::println);
    }

}