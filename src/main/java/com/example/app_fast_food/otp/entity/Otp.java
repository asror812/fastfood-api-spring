package com.example.app_fast_food.otp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "otps", timeToLive = 86400)
@Getter
@Setter
public class Otp {

    @Id
    private String phoneNumber;
    private int code;
    private int sendCount;
    private LocalDateTime createdAt;
    private LocalDateTime lastSendTime;
    private boolean isVerified;
}
