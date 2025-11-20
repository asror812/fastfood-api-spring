package com.example.app_fast_food.common.notification.sms;



public interface SmsNotificationService {

    void sendNotification(String phoneNumber , String message);
}
