package com.example.app_fast_food.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExpirationCleanupScheduler {

    private final ExpirationCleanupService cleanupService;

    @Scheduled(cron = "0 0 12 * * *")
    public void run() {
        cleanupService.cleanExpired();
    }
}
