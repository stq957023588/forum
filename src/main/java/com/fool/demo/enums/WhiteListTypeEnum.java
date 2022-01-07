package com.fool.demo.enums;

import com.fool.demo.utils.PrimarySelector;

/**
 * @author fool
 * @date 2021/12/30 14:23
 */
public enum WhiteListTypeEnum {
    Authority("authority"),
    Menu("menu"),
    ;

    private final String code;

    WhiteListTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public <T> T getRelatedObject(PrimarySelector<T> mapper, Long primaryKey) {
        return mapper.selectByPrimaryKey(primaryKey);
    }


}
