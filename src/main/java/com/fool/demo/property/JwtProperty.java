package com.fool.demo.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author fool
 * @date 2021/10/21 14:14
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperty {

    private String issuer;

    private String tokenHeader;

    private String secret;

    private Long expiration;

    private String tokenPrefix;

}
