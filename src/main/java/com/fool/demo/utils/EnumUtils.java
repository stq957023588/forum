package com.fool.demo.utils;

import java.util.function.Function;

/**
 * @author fool
 * @date 2021/12/30 14:32
 */
public class EnumUtils {

    public static <T extends Enum<T>, R> T getByFiledValue(T[] enums, Function<T, R> getter, R target) {
        if (target == null) {
            return null;
        }
        for (T tEnum : enums) {
            R apply = getter.apply(tEnum);
            if (target.equals(apply)){
                return tEnum;
            }
        }
        return null;
    }


}
