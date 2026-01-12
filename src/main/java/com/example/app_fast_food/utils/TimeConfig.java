package com.example.app_fast_food.utils;

import java.time.Clock;
import java.time.ZoneId;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeConfig {
    @Bean
    Clock clock() {
        return Clock.system(ZoneId.of(("Asia/Tashkent")));
    }
}
