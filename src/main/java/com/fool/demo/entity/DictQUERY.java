package com.fool.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fool
 * @date 2022/1/7 15:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictQUERY extends CommonQUERY{
    private Integer dictType;
}
