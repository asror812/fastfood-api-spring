package com.example.app_fast_food.notification.sms;

public interface SmsNotificationService {

    void sendNotification(String phoneNumber, String message);
}
