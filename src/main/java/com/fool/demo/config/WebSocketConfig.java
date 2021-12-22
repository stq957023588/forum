package com.fool.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@EnableWebSocket
@Configuration
public class WebSocketConfig {

    private static final int MAX_TEXT_MESSAGE_SIZE = 1024;
    private static final int MAX_BINARY_MESSAGE_SIZE = 1024;

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public ServletServerContainerFactoryBean servletServerContainerFactoryBean() {
        ServletServerContainerFactoryBean servletServerContainerFactoryBean = new ServletServerContainerFactoryBean();
        // 设置最大二进制消息
        servletServerContainerFactoryBean.setMaxBinaryMessageBufferSize(MAX_TEXT_MESSAGE_SIZE);
        // 设置最大Text消息
        servletServerContainerFactoryBean.setMaxTextMessageBufferSize(MAX_BINARY_MESSAGE_SIZE);
        return servletServerContainerFactoryBean;
    }
}
