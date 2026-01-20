package com.example.app_fast_food.productimage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mapstruct.Mapper;

import com.example.app_fast_food.product.dto.ProductImageResponseDto;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    String BASE_DOWNLOAD_URL = "/attachments/download/";

    default List<ProductImageResponseDto> toProductImageResponseDto(List<ProductImage> pis) {
        if (pis == null || pis.isEmpty())
            return Collections.emptyList();

        List<ProductImageResponseDto> productImages = new ArrayList<>();

        for (ProductImage pi : pis) {
            if (pi == null || pi.getAttachment() == null) {
                continue;
            }
            productImages.add(
                    new ProductImageResponseDto(
                            pi.getId(), BASE_DOWNLOAD_URL + pi.getAttachment().getId(), pi.getPosition()));
        }
        return productImages;
    }
}
