package com.fool.demo.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fool
 * @date 2021/10/12 10:13
 */
public enum ThreadPoolEnum {

    NAMED_THREAD_POOL("TEST");

    private final ExecutorService executorService;

    ThreadPoolEnum(String name) {
        executorService = new ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), new NamedThreadFactory(name));
    }

    public void execute(Runnable command) {
        executorService.execute(command);
    }

    public void shutdown() {
        executorService.shutdown();
    }

}
