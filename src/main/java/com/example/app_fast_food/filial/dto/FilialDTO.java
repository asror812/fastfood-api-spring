package com.example.app_fast_food.filial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;

import com.example.app_fast_food.filial.entity.Region;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilialDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String destination;

    @NotNull
    private LocalTime openAt;

    @NotNull
    private Region region;

    @NotNull
    private LocalTime closeAt;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;
}
