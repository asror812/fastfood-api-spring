package com.example.app_fast_food.attachment.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttachmentResponseDTO {

    private UUID id;

    private String originalName;

    private String storedName;

    private String contentType;

    private Long size;

    private String downloadUrl;
}
