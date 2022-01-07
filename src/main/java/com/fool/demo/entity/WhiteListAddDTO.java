package com.fool.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @author fool
 * @date 2021/12/30 14:51
 */
@Data
public class WhiteListAddDTO {

    private List<Integer> whiteIdList;

    private String type;

}
