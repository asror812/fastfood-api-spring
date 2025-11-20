package com.example.app_fast_food.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "security.token")
@Getter
@Setter
public class TokenProperties {
    private long expiration;
    private String secretKey;
}
