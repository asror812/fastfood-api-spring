package com.example.app_fast_food.utils;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.stereotype.Service;

import com.example.app_fast_food.bonus.BonusRepository;
import com.example.app_fast_food.discount.DiscountRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpirationCleanupService {

    private final DiscountRepository discountRepository;
    private final BonusRepository bonusRepository;

    private final Clock clock;

    @Transactional
    public void cleanExpired() {
        LocalDate today = LocalDate.now(clock);

        int deletedDiscounts = discountRepository.deactivateAllExpired(today);
        int deletedBonuses = bonusRepository.deactivateAllExpired(today);
        log.info("Deactivated discounts: {}, Deactivated bonuses: {}", deletedDiscounts, deletedBonuses);

    }
}
