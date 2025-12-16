package com.example.app_fast_food.user;

import com.example.app_fast_food.exception.AlreadyExistsException;
import com.example.app_fast_food.exception.InvalidCredentialsException;
import com.example.app_fast_food.exception.PhoneNumberNotVerifiedException;
import com.example.app_fast_food.otp.OtpRepository;
import com.example.app_fast_food.otp.entity.Otp;
import com.example.app_fast_food.security.JwtService;

import com.example.app_fast_food.user.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.app_fast_food.user.dto.SignInDto;
import com.example.app_fast_food.user.dto.SignUpDto;
import com.example.app_fast_food.user.dto.TokenResponseDto;
import com.example.app_fast_food.user.dto.UserResponseDto;
import com.example.app_fast_food.user.dto.UserUpdateDto;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserMapper mapper;
    private final UserRepository repository;
    private final OtpRepository otpRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    protected UserResponseDto create(SignUpDto signUpDTO) {
        String phoneNumber = signUpDTO.getPhoneNumber();

        isPhoneNumberVerified(phoneNumber);

        if (repository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new AlreadyExistsException("User", "phone number", phoneNumber);
        }

        String password = passwordEncoder.encode(signUpDTO.getPassword());

        User user = new User(null, phoneNumber, signUpDTO.getName(), password,
                signUpDTO.getBirthDate());

        repository.save(user);
        return mapper.toResponseDto(user);
    }

    private void isPhoneNumberVerified(String phoneNumber) {
        Otp otp = otpRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new PhoneNumberNotVerifiedException(phoneNumber));

        if (!otp.isVerified()) {
            throw new PhoneNumberNotVerifiedException(phoneNumber);
        }
    }

    protected UserResponseDto update(UUID id, UserUpdateDto userUpdateDTO) {
        return mapper.toResponseDto(repository.findById(id).orElseThrow());
    }

    @Transactional
    public TokenResponseDto signIn(SignInDto signInDTO) {
        User user = repository.findByPhoneNumber(signInDTO.getPhoneNumber())
                .orElseThrow(
                        () -> new InvalidCredentialsException(signInDTO.getPhoneNumber(), signInDTO.getPassword()));

        if (!passwordEncoder.matches(signInDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Username or password is not correct");
        }

        return new TokenResponseDto(jwtService.generateToken(signInDTO.getPhoneNumber()));
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Optional<User> optionalUser = repository.findByPhoneNumber(phoneNumber);

        return optionalUser.orElseThrow(
                () -> new UsernameNotFoundException("User not found with phone number %s".formatted(phoneNumber)));
    }
}
