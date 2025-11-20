package com.example.app_fast_food.attachment;

import com.example.app_fast_food.attachment.dto.AttachmentResponseDTO;
import com.example.app_fast_food.attachment.entity.Attachment;
import com.example.app_fast_food.common.mapper.BaseMapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttachmentMapper extends BaseMapper<Attachment, AttachmentResponseDTO> {

}
