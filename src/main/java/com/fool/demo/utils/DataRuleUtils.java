package com.fool.demo.utils;

import java.util.List;

/**
 * @author fool
 * @date 2022/1/12 9:23
 */
public class DataRuleUtils {

    private static final ThreadLocal<List<ExtraCondition>> THREAD_LOCAL = new ThreadLocal<>();

    public static void setDataRules(List<ExtraCondition> dataRules) {
        THREAD_LOCAL.set(dataRules);
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }

    public static List<ExtraCondition> getDataRules() {
        return THREAD_LOCAL.get();
    }


}
