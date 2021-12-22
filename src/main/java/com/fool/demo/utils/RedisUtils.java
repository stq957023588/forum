package com.fool.demo.utils;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

public class RedisUtils {
    public static Long getIncr(String key) {
        RedisConnectionFactory factory = SpringContextUtils.getBean(RedisConnectionFactory.class);
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, factory);
        // entityIdCounter.expire(0, TimeUnit.SECONDS);
        return entityIdCounter.getAndIncrement();
    }

    public static void main(String[] args) {
    }
}
