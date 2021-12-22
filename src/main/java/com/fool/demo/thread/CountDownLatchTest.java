package com.fool.demo.thread;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * @author fool
 * @date 2021/10/12 10:12
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(8);
        final Random random = new Random();
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            ThreadPoolEnum.NAMED_THREAD_POOL.execute(() -> {
                try {
                    Thread.sleep(random.nextInt(10) * 1000 + 1000);
                    list.add(UUID.randomUUID().toString());
                    System.out.println("Added");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 每结束一个线程倒计时一次
                    countDownLatch.countDown();
                }
            });
        }
        // 等到倒计时结束
        countDownLatch.await();

        list.forEach(System.out::println);

    }

}
