package com.example.app_fast_food.product.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentCreateDto {
    @NotNull
    private MultipartFile file;
}
