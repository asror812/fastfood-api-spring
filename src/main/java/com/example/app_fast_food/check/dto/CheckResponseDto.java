package com.example.app_fast_food.check.dto;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckResponseDto extends CheckDto {
    private UUID id;

}
