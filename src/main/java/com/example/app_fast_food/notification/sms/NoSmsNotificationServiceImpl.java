package com.example.app_fast_food.notification.sms;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "local")
public class NoSmsNotificationServiceImpl implements SmsNotificationService {
    @Override
    public void sendNotification(String phoneNumber, String message) {
        log.info("SMS (LOCAL) -> {}: {}", phoneNumber, message);
    }
}
