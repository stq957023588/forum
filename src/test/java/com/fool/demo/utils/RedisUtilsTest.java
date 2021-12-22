package com.fool.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RedisUtilsTest {
    // private RedisTemplate<Object, Object> redisTemplate;
    //
    // @Autowired
    // public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
    //     this.redisTemplate = redisTemplate;
    // }
    //
    // private MongoTemplate template;
    //
    // @Autowired
    // public void setTemplate(MongoTemplate template) {
    //     this.template = template;
    // }
    //
    // @Test
    // public void directoryCreateTest() {
    //     redisTemplate.opsForValue().set("user::key", "value");
    //     // Object o = redisTemplate.opsForValue().get("user::key");
    //     // System.out.println(o);
    // }
    //
    // @Test
    // public void getIncr() {
    //
    //     ExecutorService executorService = new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
    //
    //     Runnable runnable = () -> {
    //         Long incr = RedisUtils.getIncr("incr-key");
    //         log.info("ID:{}", incr);
    //
    //         PrivateChatMessage privateChatMessage = new PrivateChatMessage();
    //         privateChatMessage.setContent("asdasda");
    //         privateChatMessage.setId(incr);
    //
    //         template.save(privateChatMessage);
    //     };
    //     for (int i = 0; i < 10; i++) {
    //         executorService.execute(runnable);
    //     }
    // }

}
