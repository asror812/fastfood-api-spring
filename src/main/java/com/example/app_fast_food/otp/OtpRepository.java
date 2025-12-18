package com.example.app_fast_food.otp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.app_fast_food.otp.entity.Otp;

import java.util.Optional;

@Repository
public interface OtpRepository extends CrudRepository<Otp, String> {
    Optional<Otp> findByPhoneNumber(String phoneNumber);
}
