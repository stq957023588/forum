package com.fool.demo.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author fool
 * @date 2021/10/15 10:21
 */
public class AvgDatabasePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        int remainder = shardingValue.getValue() % availableTargetNames.size();
        int i = 0;
        for (String string : availableTargetNames) {
            if (i++ == remainder) {
                return string;
            }
        }
        throw new RuntimeException("未找到Database");
    }
}
