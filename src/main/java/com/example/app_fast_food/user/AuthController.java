package com.example.app_fast_food.user;

import com.example.app_fast_food.common.response.ApiMessageResponse;
import com.example.app_fast_food.otp.OtpService;
import com.example.app_fast_food.otp.dto.ValidatePhoneNumberDTO;
import com.example.app_fast_food.user.dto.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final OtpService otpService;

    @PostMapping("/validate")
    public ResponseEntity<ApiMessageResponse> validatePhoneNumber(
            @Valid @RequestBody ValidatePhoneNumberDTO validatePhoneNumber) {

        return ResponseEntity.status(HttpStatus.OK).body(otpService.sendSms(validatePhoneNumber));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDto> signUp(@Valid @RequestBody SignUpDto signUpDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.create(signUpDTO));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenResponseDto> signIn(@Valid @RequestBody SignInDto signInDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.signIn(signInDTO));
    }
}
