package com.fool.demo.spider.pipeline;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * @author fool
 * @date 2021/8/26 10:27
 */
@Slf4j
public class DefaultSpringPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        log.info("Get page:{}", resultItems.getRequest().getUrl());
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            log.info("{}:\t{}", entry.getKey(), entry.getValue());
        }
    }
}
