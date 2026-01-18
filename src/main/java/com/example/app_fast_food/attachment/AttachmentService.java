package com.example.app_fast_food.attachment;

import com.example.app_fast_food.attachment.dto.AttachmentResponseDto;
import com.example.app_fast_food.attachment.entity.Attachment;
import com.example.app_fast_food.exception.EntityNotFoundException;
import com.example.app_fast_food.exception.FileNotFoundException;
import com.example.app_fast_food.exception.FileReadException;
import com.example.app_fast_food.exception.FileSaveException;
import com.example.app_fast_food.exception.FileSizeLimitExceedException;
import com.example.app_fast_food.product.ProductRepository;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.productimage.ProductImage;
import com.example.app_fast_food.productimage.ProductImageRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentService {

    @Value("${api.storage.images-path}")
    private String imagesFolderPath;

    @Value("${api.images.base.download-url}")
    private String downloadBaseUrl;

    private final AttachmentRepository repository;

    private final AttachmentMapper mapper;

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

    private static final String ATTACHMENT_ENTITY = "Image";

    private static final long LIMIT_BYTES = 1L * 1024 * 1024;

    @Transactional
    public AttachmentResponseDto uploadProductImage(MultipartFile file, UUID productId, int position) {
        if (file == null || file.isEmpty())
            throw new IllegalArgumentException("File is empty");

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed. contentType=" + contentType);
        }

        long size = file.getSize();
        if (size > LIMIT_BYTES) {
            throw new FileSizeLimitExceedException("Image exceeded limit size: 1 MB", 1L);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product", productId.toString()));

        if (position < 1)
            throw new IllegalArgumentException("Invalid position.");

        boolean positionOccupied = product.getImages().stream()
                .anyMatch(pi -> pi.getPosition() == position);
        if (positionOccupied) {
            throw new IllegalArgumentException(
                    "Image position `%s` is already occupied for product `%s`".formatted(position, productId));
        }

        UUID id = UUID.randomUUID();

        String originalName = safeOriginalName(file.getOriginalFilename());
        String storedName = UUID.randomUUID() + getExtension(originalName);

        Attachment attachment = new Attachment(
                id,
                originalName,
                storedName,
                contentType,
                size);

        repository.save(attachment);

        Path targetPath = Paths.get(imagesFolderPath, storedName);

        try (InputStream inputStream = file.getInputStream()) {

            Files.createDirectories(targetPath.getParent());
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            repository.deleteById(id);
            throw new FileSaveException("Failed to save file to disk: " + targetPath, e);
        }

        try {
            ProductImage productImage = new ProductImage(product, attachment, position);
            productImageRepository.save(productImage);
            product.getImages().add(productImage);
        } catch (RuntimeException e) {
            try {
                Files.deleteIfExists(targetPath);
            } catch (IOException ioe) {
                log.warn("Failed to cleanup file after ProductImage save failure: {}", targetPath, ioe);
            }
            repository.deleteById(id);
            throw e;
        }

        return mapper.toResponseDTO(attachment);
    }

    public void loadImageFromImageFolder(@NonNull UUID id, HttpServletResponse response) {
        Attachment image = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ATTACHMENT_ENTITY, id.toString()));

        Path path = Paths.get(imagesFolderPath, image.getStoredName());
        File file = path.toFile();

        if (!file.exists())
            throw new FileNotFoundException(image.getStoredName(), path.toString());

        response.setContentType(image.getContentType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getOriginalName() + "\"");

        try (InputStream inputStream = new FileInputStream(file)) {
            inputStream.transferTo(response.getOutputStream());
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException(file.getName(), file.getPath());
        } catch (IOException e) {
            throw new FileReadException("Failed to read image from disk: " + image.getStoredName(), e);
        }
    }

    @Transactional
    public void delete(UUID productImageId) {
        ProductImage productImage = productImageRepository.findById(productImageId)
                .orElseThrow(() -> new EntityNotFoundException("ProductImage", productImageId.toString()));

        UUID attachmentId = productImage.getAttachment().getId();
        String storedName = productImage.getAttachment().getStoredName();

        productImageRepository.delete(productImage);

        Path path = Paths.get(imagesFolderPath, storedName);

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.warn("Failed to delete file from disk: {}", path, e);
        }

        repository.deleteById(attachmentId);

    }

    private static String getExtension(String originalName) {
        if (originalName == null)
            return "";
        int dot = originalName.lastIndexOf('.');
        return (dot >= 0) ? originalName.substring(dot) : "";
    }

    private static String safeOriginalName(String name) {
        return (name == null || name.isBlank()) ? "file" : name;
    }

    public List<AttachmentResponseDto> findAll() {
        return repository.findAll().stream().map(mapper::toResponseDTO).toList();
    }

    public AttachmentResponseDto findById(UUID id) {
        return repository.findById(id).map(mapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Attachment", id.toString()));
    }

    public AttachmentResponseDto uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty())
            throw new IllegalArgumentException("File is empty");

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed. contentType=" + contentType);
        }

        long size = file.getSize();
        if (size > LIMIT_BYTES)
            throw new FileSizeLimitExceedException("Image exceeded limit size: 1 MB", 1L);

        UUID id = UUID.randomUUID();

        String originalName = safeOriginalName(file.getOriginalFilename());
        String storedName = UUID.randomUUID() + getExtension(originalName);

        Attachment attachment = new Attachment(
                id,
                originalName,
                storedName,
                contentType,
                size);

        repository.save(attachment);

        Path targetPath = Paths.get(imagesFolderPath, storedName);

        try (InputStream inputStream = file.getInputStream()) {

            Files.createDirectories(targetPath.getParent());
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            repository.deleteById(id);
            throw new FileSaveException("Failed to save file to disk: " + targetPath, e);
        }

        return mapper.toResponseDTO(attachment);
    }
}
