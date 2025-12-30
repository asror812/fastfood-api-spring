package com.example.app_fast_food.discount;

import com.example.app_fast_food.discount.dto.DiscountResponseDto;
import com.example.app_fast_food.exception.EntityNotFoundException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Getter
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository repository;
    private final DiscountMapper mapper;

    @Cacheable("discounts")
    public List<DiscountResponseDto> findAll() {
        return repository.findAllActiveDiscountsDetails(LocalDate.now()).stream().map(mapper::toResponseDTO).toList();
    }

    @Cacheable(value = "discountById", key = "#p0")
    public DiscountResponseDto findById(UUID id) {
        LocalDate now = LocalDate.now();
        return repository.findDiscountDetails(id, now).map(mapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Discount", id.toString()));
    }

}
