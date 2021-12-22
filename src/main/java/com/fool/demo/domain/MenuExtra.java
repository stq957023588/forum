package com.fool.demo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fool
 * @date 2021/12/20 16:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuExtra extends Menu{

    private String role;

}
