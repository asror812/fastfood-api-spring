package com.example.app_fast_food.otp;

import com.example.app_fast_food.common.notification.sms.SmsNotificationService;
import com.example.app_fast_food.common.response.ApiMessageResponse;
import com.example.app_fast_food.exceptions.EntityNotFoundException;
import com.example.app_fast_food.exceptions.OtpEarlyResentException;
import com.example.app_fast_food.exceptions.OtpLimitExitedException;
import com.example.app_fast_food.otp.dto.ValidatePhoneNumberDTO;
import com.example.app_fast_food.otp.entity.Otp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepository otpRepository;
    private final Random random = new Random();
    private final String VERIFICATION_MASSAGE = "Your verification code is: %d%n";

    @Value("${spring.otp.retry-wait-time}")
    private int retryWaitTime;

    @Value("${spring.otp.retry-count}")
    private int retryCount;

    @Value("${spring.otp.time-to-live}")
    private int timeToLive;

    private final SmsNotificationService smsNotificationService;

    public ApiMessageResponse sendSms(ValidatePhoneNumberDTO validatePhoneNumberDTO) {
        String phoneNumber = validatePhoneNumberDTO.getPhoneNumber();
        Optional<Otp> existingOtp = otpRepository.findByPhoneNumber(phoneNumber);

        if (validatePhoneNumberDTO.getOtp() == null) {
            if (existingOtp.isPresent()) {
                return reTry(existingOtp.get());
            }
            
            Otp otp = sendSmsInternal(phoneNumber);
            otpRepository.save(otp);
            return new ApiMessageResponse("Sms was sent successfully");
        }

        if (existingOtp.isEmpty()) {
            throw new EntityNotFoundException("Otp", phoneNumber);
        }
        Otp otp = existingOtp.orElseThrow(() -> new RuntimeException("We didnt send any verification"));

        if (otp.getCode() == validatePhoneNumberDTO.getOtp()) {
            otp.setVerified(true);
            otpRepository.save(otp);
            return new ApiMessageResponse("Otp was successfully verified");
        } else
            return new ApiMessageResponse("Otp was incorrect");
    }

    private ApiMessageResponse reTry(Otp otp) {
        if (otp.getLastSendTime().plusSeconds(retryWaitTime).isAfter(LocalDateTime.now())) {
            long resentTime = Duration.between(otp.getLastSendTime(), LocalDateTime.now()).getSeconds();
            throw new OtpEarlyResentException(retryWaitTime - resentTime);
        }

        if (otp.getSendCount() >= retryCount) {
            throw new OtpLimitExitedException(otp.getSendCount(),
                    otp.getCreatedAt().plusSeconds(timeToLive));
        }

        Otp otp1 = sendSmsInternal(otp);
        otpRepository.save(otp1);

        return new ApiMessageResponse("Sms was re-send successfully");
    }

    private Otp sendSmsInternal(String phoneNumber) {
        int code = random.nextInt(10000, 99999);
        smsNotificationService.sendNotification(phoneNumber, VERIFICATION_MASSAGE.formatted(code));
        return new Otp(phoneNumber, code, 1, LocalDateTime.now(), LocalDateTime.now(), false);
    }

    private Otp sendSmsInternal(Otp otp) {
        int code = random.nextInt(10000, 99999);
        smsNotificationService.sendNotification(otp.getPhoneNumber(), VERIFICATION_MASSAGE.formatted(code));

        otp.setLastSendTime(LocalDateTime.now());
        otp.setSendCount(otp.getSendCount() + 1);
        otp.setCode(code);

        return otp;
    }
}
