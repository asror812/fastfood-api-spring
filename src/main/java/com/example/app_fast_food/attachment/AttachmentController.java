package com.example.app_fast_food.attachment;

import com.example.app_fast_food.attachment.dto.AttachmentResponseDto;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AttachmentResponseDto upload(@RequestPart("file") MultipartFile file) {
        return attachmentService.uploadImage(file);
    }

    @GetMapping
    public ResponseEntity<List<AttachmentResponseDto>> getAll() {
        return ResponseEntity.ok(attachmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttachmentResponseDto> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(attachmentService.findById(id));
    }

    @GetMapping("/download/{id}")
    public void load(@PathVariable("id") UUID id, HttpServletResponse response) {
        attachmentService.loadImageFromImageFolder(id, response);
    }

    @DeleteMapping("/product-images/{id}")
    public void delete(@PathVariable("id") UUID id) {
        attachmentService.delete(id);
    }

}
