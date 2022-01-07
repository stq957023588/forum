package com.fool.demo.entity;

import lombok.Data;

/**
 * @author fool
 * @date 2021/12/28 9:32
 */
@Data
public class WhiteListDTO {
    private Integer id ;

    private String name;

    private String path;

    private String method;

    public WhiteListDTO() {
    }

    public WhiteListDTO(Integer id) {
        this.id = id;
    }
}
