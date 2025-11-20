package com.example.app_fast_food.common.notification.sms;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "spring.profiles.active" , havingValue = "local")
public class NoSmsNotificationServiceImpl implements SmsNotificationService {
    @Override
    public void sendNotification(String phoneNumber, String message) {
        System.out.printf("%s: %s \n" , phoneNumber, message);
    }
}
