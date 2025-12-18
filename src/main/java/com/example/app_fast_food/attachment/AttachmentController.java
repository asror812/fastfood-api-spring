package com.example.app_fast_food.attachment;

import com.example.app_fast_food.attachment.dto.AttachmentResponseDto;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping("/upload")
    public AttachmentResponseDto upload(MultipartHttpServletRequest request) {
        return attachmentService.uploadImageToFileSystem(request);
    }

    @GetMapping
    public ResponseEntity<List<AttachmentResponseDto>> getAll() {
        return ResponseEntity.ok(attachmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttachmentResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(attachmentService.findById(id));
    }

    @GetMapping("/download/{id}")
    public void load(@PathVariable UUID id,
            HttpServletResponse response) {
        attachmentService.loadImageFromImageFolder(id, response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        attachmentService.delete(id);
    }

}
