package com.fool.demo.config;

import com.fool.demo.spider.pageprocessor.JianshuPageProcessor;
import com.fool.demo.spider.pipeline.DefaultSpringPipeline;
import org.springframework.context.annotation.Bean;
import us.codecraft.webmagic.Spider;

/**
 * @author fool
 * @date 2021/8/26 10:32
 */
// @Configuration
public class SpiderConfig {

    @Bean(name = "Jianshu")
    public Spider jianshuSpider() {
        return Spider.create(new JianshuPageProcessor())
                .addUrl("https://www.jianshu.com/")
                .addPipeline(new DefaultSpringPipeline())
                .thread(2);
    }

}
