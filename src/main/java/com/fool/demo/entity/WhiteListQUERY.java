package com.fool.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fool
 * @date 2021/12/28 9:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WhiteListQUERY extends CommonQUERY{

    private String type;

}
