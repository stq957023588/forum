package com.fool.demo.entity;

import com.fool.demo.utils.Dict;
import lombok.Data;

/**
 * @author fool
 * @date 2022/1/7 10:39
 */
@Data
public class DictDTO implements Dict<Integer,Integer> {

    private Integer type;

    private Integer code;

    private String label;

    private String value;

}
