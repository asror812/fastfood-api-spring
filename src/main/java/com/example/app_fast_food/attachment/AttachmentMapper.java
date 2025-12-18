package com.example.app_fast_food.attachment;

import org.mapstruct.Mapper;

import com.example.app_fast_food.attachment.dto.AttachmentResponseDto;
import com.example.app_fast_food.attachment.entity.Attachment;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    AttachmentResponseDto toResponseDTO(Attachment attachment);
}
