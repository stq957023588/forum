package com.fool.demo.runners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * @author fool
 * @date 2021/8/26 10:34
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "spider.enable", havingValue = "true")
public class SpiderRunnerImpl implements ApplicationRunner {

    private final List<Spider> spiders;

    public SpiderRunnerImpl(List<Spider> spiders) {
        this.spiders = spiders;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("The spider starts to catch");
        spiders.forEach(Spider::start);
    }
}
