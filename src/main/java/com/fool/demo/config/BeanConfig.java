package com.fool.demo.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author fool
 * @date 2021/12/14 14:22
 */
@Configuration
public class BeanConfig {

    @Bean("namedThreadPool")
    public TaskExecutor namedThreadFactory() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(10);
        // 设置最大线程数
        executor.setMaxPoolSize(15);
        // 设置队列容量
        executor.setQueueCapacity(20);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("Fool-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

    /**
     * 使用fastjson将所有的返回值都转成json格式,并将默认的Content-Type设置为application/json
     * @return 自定义的HttpMessageConverter
     */
    @Bean
    public HttpMessageConverter<?> fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        // 此处获取的是不可修改的list,所以在下面需要重新创建一个list实例
        List<MediaType> supportedMediaTypes = fastJsonHttpMessageConverter.getSupportedMediaTypes();
        MediaType mediaType = new MediaType("application", "json", 1.0);

        ArrayList<MediaType> mediaTypes = new ArrayList<>(supportedMediaTypes);
        mediaTypes.add(mediaType);

        fastJsonHttpMessageConverter.setSupportedMediaTypes(Collections.unmodifiableList(mediaTypes));
        return fastJsonHttpMessageConverter;
    }

}
