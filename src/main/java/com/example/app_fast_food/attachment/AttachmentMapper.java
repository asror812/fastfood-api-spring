package com.example.app_fast_food.attachment;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.app_fast_food.attachment.dto.AttachmentResponseDto;
import com.example.app_fast_food.attachment.entity.Attachment;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    String BASE_DOWNLOAD_URL = "/attachments/download/";

    @Mapping(target = "downloadUrl", ignore = true)
    AttachmentResponseDto toResponseDTO(Attachment attachment);

    @AfterMapping
    default void setDownloadUrl(
            Attachment attachment,
            @MappingTarget AttachmentResponseDto dto) {
        if (attachment.getId() != null) {
            dto.setDownloadUrl(BASE_DOWNLOAD_URL + attachment.getId());
        }
    }
}
