package com.example.app_fast_food.attachment;

import com.example.app_fast_food.attachment.dto.AttachmentResponseDto;
import com.example.app_fast_food.attachment.entity.Attachment;
import com.example.app_fast_food.exception.EntityNotFoundException;
import com.example.app_fast_food.exception.FileNotFoundException;
import com.example.app_fast_food.exception.FileReadException;
import com.example.app_fast_food.exception.FileSaveException;
import com.example.app_fast_food.exception.FileSizeLimitExceedException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
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

    @Value("${api.storage.images-path}")
    private String imagesFolderPath;

    @Value("${api.images.base.download-url}")
    private String downloadUrl;

    private final AttachmentRepository repository;
    private final AttachmentMapper mapper;

    private static final Long LIMIT_SIZE = 1L;

    public AttachmentResponseDto uploadImageToFileSystem(HttpServletRequest request) {
        Part file;

        try {
            file = request.getPart("file");
        } catch (IOException | ServletException e) {
            throw new FileNotFoundException("file part not found in request");
        }

        UUID id = UUID.randomUUID();
        String originalName = file.getSubmittedFileName();
        String contentType = file.getContentType();
        long size = file.getSize();

        if (size > 1024 * 1024) {
            throw new FileSizeLimitExceedException("Image exceeded limit size: 1 MB", LIMIT_SIZE);
        }

        String storedName = UUID.randomUUID() + getExtension(originalName);

        Attachment attachment = new Attachment(
                id,
                originalName,
                storedName,
                contentType,
                size,
                downloadUrl + id);

        repository.save(attachment);

        Path targetPath = Paths.get(imagesFolderPath, storedName);

        try (InputStream inputStream = file.getInputStream()) {
            Files.createDirectories(targetPath.getParent());
            Files.copy(inputStream, targetPath);
        } catch (IOException e) {
            repository.delete(attachment);
            throw new FileSaveException("Failed to save file to disk: " + targetPath, e);
        }

        return mapper.toResponseDTO(attachment);
    }

    private String getExtension(String originalName) {
        int dot = originalName.lastIndexOf('.');
        return (dot >= 0) ? originalName.substring(dot) : "";
    }

    public void loadImageFromImageFolder(@NonNull UUID id, HttpServletResponse response) {
        Attachment image = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Image", id.toString()));

        String filePath = imagesFolderPath + image.getStoredName();

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(image.getStoredName(), filePath);
        }

        response.setContentType(image.getContentType());

        try (InputStream inputStream = new FileInputStream(file)) {
            inputStream.transferTo(response.getOutputStream());

        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException(file.getName(), file.getPath());

        } catch (IOException e) {
            throw new FileReadException("Failed to read image from disk: " + image.getStoredName(), e);
        }
    }

    public void delete(@NonNull UUID id) {
        repository.deleteById(id);
    }

    public List<AttachmentResponseDto> findAll() {
        return repository.findAll().stream().map(mapper::toResponseDTO).toList();
    }

    public AttachmentResponseDto findById(UUID id) {
        return repository.findById(id).map(mapper::toResponseDTO).orElseThrow(
                () -> new EntityNotFoundException("Attachmen", id.toString()));
    }
}
