package com.example.app_fast_food.notification.sms;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.example.app_fast_food.notification.eskiz.EskizFeignClient;
import com.example.app_fast_food.notification.eskiz.dto.SendSmsRequestDTO;

@Service
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "production")
@Slf4j
@RequiredArgsConstructor
public class SmsNotificationServiceImpl implements SmsNotificationService {

    @Value("${notification.eskiz.token}")
    private String token;

    private final EskizFeignClient eskizFeignClient;

    @Override
    public void sendNotification(String phoneNumber, String message) {
        try {
            eskizFeignClient.sendSms("Bearer " + token, new SendSmsRequestDTO(phoneNumber, message));
        } catch (FeignException.Unauthorized e) {
            token = eskizFeignClient.refresh("Bearer " + token).getData().get(token);
            eskizFeignClient.sendSms("Bearer " + token, new SendSmsRequestDTO(phoneNumber, message));
        } catch (Exception e) {
            log.error("Exception occurred while sending notification.phoneNumber {}  message  {}  ", phoneNumber,
                    message, e);
        }
    }
}
