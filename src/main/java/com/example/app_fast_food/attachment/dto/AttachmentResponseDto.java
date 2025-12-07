package com.example.app_fast_food.attachment.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentResponseDto {
    private UUID id;

    private String originalName;

    private String storedName;

    private String contentType;

    private Long size;

    private String downloadUrl;

    @Override
    public String toString() {
        return "AttachmentResponseDto [originalName=" + originalName +
                ", storedName=" + storedName +
                ", downloadUrl=" + downloadUrl + "]";
    }
}
