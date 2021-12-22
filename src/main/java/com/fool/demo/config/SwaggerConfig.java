package com.fool.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfig {

    private boolean enable;

    // swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean
    public Docket createInternalRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Group internal")
                .apiInfo(apiInfo())
                // 是否开启
                .enable(enable)
                .select()
                // 包路径
                .apis(RequestHandlerSelectors.basePackage("com.fool.demo.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createExternalRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Group external")
                .apiInfo(apiInfo())
                .enable(enable)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fool.demo.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    // 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("Fool's demo")
                // 创建人信息
                .contact(new Contact("Fool", "www.fool.com", "fool@fool.com"))
                // 版本号
                .version("1.0")
                // 描述
                .description("This is a demo application")
                .build();
    }
}