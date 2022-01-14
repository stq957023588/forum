package com.fool.demo.annotation;

import java.lang.annotation.*;

/**
 * @author fool
 * @date 2022/1/13 11:32
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataAuthority {
}
