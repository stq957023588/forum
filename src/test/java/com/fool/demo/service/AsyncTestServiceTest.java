package com.fool.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author fool
 * @date 2021/12/14 14:29
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class AsyncTestServiceTest {

    public AsyncTestService asyncTestService;

    @Autowired
    public void setAsyncTestService(AsyncTestService asyncTestService) {
        this.asyncTestService = asyncTestService;
    }

    @Test
    public void noneReturnAsyncTest() {
        long start = System.currentTimeMillis();

        asyncTestService.noneReturnAsyncTest5000();
        asyncTestService.noneReturnAsyncTest7000();

        log.info("Cost:" + (System.currentTimeMillis() - start));
    }

}