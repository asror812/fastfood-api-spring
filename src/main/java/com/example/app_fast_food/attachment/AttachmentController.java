package com.example.app_fast_food.attachment;

import com.example.app_fast_food.attachment.dto.AttachmentResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping("/upload")
    public AttachmentResponseDTO upload(MultipartHttpServletRequest request) {
        try {
            return attachmentService.uploadImageToFileSystem(request);

        } catch (IOException | ServletException e) {
            throw new RuntimeException();
        }
    }

    @GetMapping("/download/{id}")
    public void load(@PathVariable UUID id,
            HttpServletResponse response) {
        try {
            attachmentService.loadImageFromImageFolder(id, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        attachmentService.delete(UUID.fromString(id));
    }

}
