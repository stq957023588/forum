package com.fool.demo.utils;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author fool
 * @date 2021/12/23 9:44
 */
public class PageUtils {

    public static <T> PageInfo<T> doSelect(ISelect select, Pageable pageable) {
        Function<T, T> transfer = o -> o;
        return doSelect(select, pageable, transfer);
    }


    public static <T, R> PageInfo<R> doSelect(ISelect select, Pageable pageable, Function<T, R> transfer) {
        Page<T> page = PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), true, null, true)
                .setOrderBy(pageable.getOrderBy())
                .doSelectPage(select);

        Collector<T, Page<R>, Page<R>> collector = new Collector<>() {
            @Override
            public Supplier<Page<R>> supplier() {
                return () -> getNonDataPage(page);
            }

            @Override
            public BiConsumer<Page<R>, T> accumulator() {
                return (p, i) -> p.add(transfer.apply(i));
            }

            @Override
            public BinaryOperator<Page<R>> combiner() {
                return (left, right) -> {
                    left.addAll(right);
                    return left;
                };
            }

            @Override
            public Function<Page<R>, Page<R>> finisher() {
                return t -> t;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
            }
        };

        Page<R> collect = page.stream().collect(collector);

        return new PageInfo<>(collect);
    }


    public static <T, R> Page<T> getNonDataPage(Page<R> page) {
        Page<T> ts = new Page<>();

        ts.setPageNum(page.getPageNum());
        ts.setPageSize(page.getPageSize());
        ts.setPages(page.getPages());
        ts.setTotal(page.getTotal());

        return ts;
    }

}
