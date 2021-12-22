package com.fool.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author fool
 * @date 2021/12/14 14:21
 */
@Slf4j
@Service
public class AsyncTestService {


    @Async("namedThreadPool")
    public void noneReturnAsyncTest5000() {
        try {
            log.info("Method will execute in 5000 mill");
            Thread.sleep(5000L);
            log.info("Method finished in 5000");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async("namedThreadPool")
    public void noneReturnAsyncTest7000() {
        try {
            log.info("Method will execute in 7000 mill");
            Thread.sleep(7000L);
            log.info("Method finished in 7000");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
