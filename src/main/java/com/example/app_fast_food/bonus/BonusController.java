package com.example.app_fast_food.bonus;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app_fast_food.bonus.dto.bonus.BonusResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bonuses")
@RequiredArgsConstructor
public class BonusController {
    private final BonusService bonusService;

    @GetMapping
    public ResponseEntity<List<BonusResponseDto>> getAll() {
        return ResponseEntity.ok(bonusService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BonusResponseDto> getBonusById(@PathVariable UUID id) {
        return ResponseEntity.ok(bonusService.findById(id));
    }

}
