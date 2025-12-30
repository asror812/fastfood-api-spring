package com.example.app_fast_food.orderitem;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.app_fast_food.orderitem.dto.OrderItemCreateRequestDTO;
import com.example.app_fast_food.orderitem.dto.OrderItemResponseDto;
import com.example.app_fast_food.orderitem.dto.ProductDto;
import com.example.app_fast_food.orderitem.entity.OrderItem;
import com.example.app_fast_food.product.entity.Product;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    String BASE_DOWNLOAD_URL = "/attachments/";

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "quantity", ignore = true)
    @Mapping(target = "unitPrice", ignore = true)
    @Mapping(target = "discountAmount", ignore = true)
    @Mapping(target = "finalPrice", ignore = true)
    @Mapping(target = "bonus", ignore = true)
    @Mapping(target = "selectedBonusId", ignore = true)
    OrderItem toEntity(OrderItemCreateRequestDTO orderItemCreateDTO);

    OrderItemResponseDto toResponseDTO(OrderItem orderItem);

    default ProductDto toProductDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        return dto;
    }
}
