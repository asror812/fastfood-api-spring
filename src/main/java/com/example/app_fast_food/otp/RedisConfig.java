package com.example.app_fast_food.otp;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackages = "com.example.app_fast_food.otp")
public class RedisConfig {
}
