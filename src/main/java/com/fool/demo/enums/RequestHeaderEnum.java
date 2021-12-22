package com.fool.demo.enums;

import com.fool.demo.consts.HttpHeaderConst;
import lombok.Getter;

/**
 * @author fool
 * @date 2021/10/18 16:16
 */
@Getter
public enum RequestHeaderEnum {
    NON_UNIFORM_RESPONSE(HttpHeaderConst.UNIFORM_RESPONSE,"none");


    private final String name;
    private final String value;

    RequestHeaderEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

}
