package com.example.app_fast_food.attachment;

import com.example.app_fast_food.attachment.dto.AttachmentResponseDto;
import com.example.app_fast_food.attachment.entity.Attachment;
import com.example.app_fast_food.exception.FileNotFoundException;
import com.example.app_fast_food.exception.FileReadException;
import com.example.app_fast_food.exception.FileSaveException;
import com.example.app_fast_food.exception.FileSizeLimitExceedException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    private final AttachmentRepository repository;
    private static final String IMAGES_FOLDER_PATH = "/home/ruby/Desktop/app_fast_food/app_fast_food/images/burger_1.jpg";
    private final AttachmentMapper mapper;
    private static final Long LIMIT_SIZE = 1L;

    public AttachmentResponseDto uploadImageToFileSystem(HttpServletRequest request) {
        Part file;

        try {
            file = request.getPart("file");
        } catch (IOException | ServletException e) {
            throw new FileNotFoundException("file part not found in request");
        }

        String originalName = file.getSubmittedFileName();
        String contentType = file.getContentType();
        long size = file.getSize();

        if (size > 1024 * 1024) {
            throw new FileSizeLimitExceedException("Image exceeded limit size: 1 MB", LIMIT_SIZE);
        }

        // Generate unique stored file name
        String storedName = UUID.randomUUID().toString() + getExtension(originalName);

        String downloadUrl = "http://localhost:8080/attachments/download/" + storedName;

        Attachment attachment = new Attachment(
                null,
                originalName,
                storedName,
                contentType,
                size,
                downloadUrl);

        log.info("Entity -> {}", attachment);
        repository.save(attachment);

        Path targetPath = Paths.get(IMAGES_FOLDER_PATH, storedName);

        try (InputStream inputStream = file.getInputStream()) {
            Files.createDirectories(targetPath.getParent());
            Files.copy(inputStream, targetPath);
        } catch (IOException e) {
            repository.delete(attachment);
            throw new FileSaveException("Failed to save file to disk: " + targetPath, e.getCause());
        }

        log.info("{}", mapper.toResponseDTO(attachment));
        return mapper.toResponseDTO(attachment);
    }

    private String getExtension(String originalName) {
        int dot = originalName.lastIndexOf('.');
        return (dot >= 0) ? originalName.substring(dot) : "";
    }

    public void loadImageFromImageFolder(@NonNull UUID id, HttpServletResponse response) {
        Attachment image = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Image with name: %s not found"));

        String filePath = image.getDownloadUrl();

        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException(image.getStoredName(), filePath);
        }

        response.setHeader("Content-Disposition", "inline; filename=\"" + image.getOriginalName() + "\"");
        try (InputStream inputStream = new FileInputStream(file)) {
            inputStream.transferTo(response.getOutputStream());
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException(file.getName(), file.getPath());
        } catch (IOException e) {
            throw new FileReadException(
                    "Failed to read image from disk: " + image.getStoredName(), e);
        }
    }

    public void delete(@NonNull UUID id) {
        Attachment entity = repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Attachment with id : %s not found"));

        repository.delete(entity);
    }

    public List<AttachmentResponseDto> getAll() {
        return repository.findAll().stream().map(mapper::toResponseDTO).toList();
    }
}
