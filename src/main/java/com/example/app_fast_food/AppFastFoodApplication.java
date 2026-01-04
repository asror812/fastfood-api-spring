package com.example.app_fast_food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableMethodSecurity
public class AppFastFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppFastFoodApplication.class, args);
	}

}
