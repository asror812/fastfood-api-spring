package com.example.app_fast_food.attachment;

import com.example.app_fast_food.attachment.dto.AttachmentResponseDto;
import com.example.app_fast_food.attachment.entity.Attachment;
import com.example.app_fast_food.exceptions.FileNotFoundException;
import com.example.app_fast_food.exceptions.FileSizeLimitExceedException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentService {

    private final AttachmentRepository repository;
    final String IMAGES_FOLDER_PATH = "/static/images";
    private final AttachmentMapper mapper;
    final Long limitSize = 1L;

    public AttachmentResponseDto uploadImageToFileSystem(HttpServletRequest request)
            throws IOException, ServletException {

        Part file = request.getPart("file");

        String originalName = file.getSubmittedFileName();
        String contentType = file.getContentType();
        long size = file.getSize();

        if (size > 1024 * 1024) { // 1 MB
            throw new FileSizeLimitExceedException("Image exceeded limit size: 1 MB", limitSize);
        }

        // Generate unique stored file name
        String storedName = UUID.randomUUID().toString() + getExtension(originalName);

        // Build download URL
        String downloadUrl = "http://localhost:8080/attachments/download/" + storedName;

        // Create and save attachment entity
        Attachment attachment = new Attachment(
                null,
                originalName,
                storedName,
                contentType,
                size,
                downloadUrl);

        log.info("Entity -> {}", attachment);
        repository.save(attachment);

        InputStream inputStream = file.getInputStream();
        Path targetPath = Paths.get(IMAGES_FOLDER_PATH, storedName);

        Files.copy(inputStream, targetPath);

        log.info("Response -> {}", mapper.toResponseDTO(attachment));
        return mapper.toResponseDTO(attachment);
    }

    private String getExtension(String originalName) {
        int dot = originalName.lastIndexOf('.');
        return (dot >= 0) ? originalName.substring(dot) : "";
    }

    public void loadImageFromImageFolder(UUID id, HttpServletResponse response) throws IOException {
        Attachment image = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Image with name: %s not found"));

        Path path = Path.of(image.getDownloadUrl());
        try (InputStream inputStream = new FileInputStream(path.toFile())) {
            String contentType = Files.probeContentType(path);

            response.setHeader("Content-Disposition", "attachment; filename=\"" + image.getId() + "\"");
            response.setContentType(contentType);

            response.getOutputStream().write(inputStream.readAllBytes());

        } catch (IOException e) {
            throw new FileNotFoundException(image.getOriginalName(), image.getDownloadUrl());
        }

    }

    public void delete(UUID id) {
        Attachment entity = repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Attachment with id : %s not found"));

        repository.delete(entity);
    }

    public List<AttachmentResponseDto> getAll() {
        return repository.findAll().stream().map(a -> mapper.toResponseDTO(a)).toList();
    }
}
