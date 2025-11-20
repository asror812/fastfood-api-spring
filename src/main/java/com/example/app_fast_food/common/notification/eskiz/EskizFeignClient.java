package com.example.app_fast_food.common.notification.eskiz;


import com.example.app_fast_food.common.notification.eskiz.dto.SendSmsRequestDTO;
import com.example.app_fast_food.common.notification.eskiz.dto.TokenRefreshResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "eskizFeignClient" , url = "https://notify.eskiz.uz/api")
public interface EskizFeignClient {


    @PatchMapping("auth/refresh")
    TokenRefreshResponseDTO refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearer);

    @PostMapping("message/sms/send")
    String sendSms(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearer , SendSmsRequestDTO sendSmsRequestDTO);
}
