package com.fool.demo.mapper;

import com.fool.demo.domain.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.fool.demo.domain.Order
 */

public interface OrderMapper {

    int insertSelective(Order order);

    List<Order> selectByValue(Integer value);

    List<Order> selectAll();

    List<Order> selectBetween(@Param("start") Integer startValue, @Param("end") Integer endValue);

    List<Order> selectGreater(@Param("value")Integer value);

}




