package com.example.app_fast_food.user;

import com.example.app_fast_food.common.mapper.BaseMapper;
import com.example.app_fast_food.common.service.GenericService;
import com.example.app_fast_food.exceptions.AlreadyRegisteredException;
import com.example.app_fast_food.exceptions.InvalidCredentialsException;
import com.example.app_fast_food.exceptions.PhoneNumberNotVerifiedException;
import com.example.app_fast_food.otp.OtpRepository;
import com.example.app_fast_food.otp.entity.Otp;
import com.example.app_fast_food.security.JwtService;

import com.example.app_fast_food.user.dto.UserResponseDTO;
import com.example.app_fast_food.user.dto.UserUpdateRequestDTO;
import com.example.app_fast_food.user.entity.User;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.app_fast_food.user.dto.SignInDTO;
import com.example.app_fast_food.user.dto.SignUpDTO;
import com.example.app_fast_food.user.dto.TokenResponseDTO;

import java.util.Optional;
import java.util.UUID;

@Service
@Getter
@Slf4j
public class AuthService extends GenericService<User, UserResponseDTO>
        implements UserDetailsService {

    private final Class<User> entityClass = User.class;
    private final UserMapper mapper;

    private final UserRepository repository;
    private final OtpRepository otpRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthService(BaseMapper<User, UserResponseDTO> baseMapper, UserRepository repository,
            OtpRepository otpRepository, UserRepository userRepository, PasswordEncoder passwordEncoder,
            JwtService jwtService, UserMapper mapper) {
        super(baseMapper);

        this.repository = repository;
        this.otpRepository = otpRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.mapper = mapper;
    }

    protected UserResponseDTO create(SignUpDTO signUpDTO) {
        String phoneNumber = signUpDTO.getPhoneNumber();

        isPhoneNumberVerified(phoneNumber);

        if (repository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new AlreadyRegisteredException(phoneNumber);
        }

        String password = passwordEncoder.encode(signUpDTO.getPassword());

        User user = new User(null, phoneNumber, signUpDTO.getName(), password,
                signUpDTO.getBirthDate());

        repository.save(user);
        return mapper.toResponseDTO(user);
    }

    private void isPhoneNumberVerified(String phoneNumber) {
        Otp otp = otpRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new PhoneNumberNotVerifiedException(phoneNumber));

        if (!otp.isVerified()) {
            throw new PhoneNumberNotVerifiedException(phoneNumber);
        }
    }

    protected UserResponseDTO update(UUID id, UserUpdateRequestDTO userUpdateDTO) {
        return mapper.toResponseDTO(repository.findById(id).orElseThrow());
    }

    @Transactional
    public TokenResponseDTO signIn(SignInDTO signInDTO) {
        User user = repository.findByPhoneNumber(signInDTO.getPhoneNumber())
                .orElseThrow(
                        () -> new InvalidCredentialsException(signInDTO.getPhoneNumber(), signInDTO.getPassword()));

        if (!passwordEncoder.matches(signInDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Username or password is not correct");
        }

        return new TokenResponseDTO(jwtService.generateToken(signInDTO.getPhoneNumber()));
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Optional<User> optionalUser = repository.findByPhoneNumber(phoneNumber);

        return optionalUser.orElseThrow(
                () -> new UsernameNotFoundException("User not found with phone number %s".formatted(phoneNumber)));
    }
}
