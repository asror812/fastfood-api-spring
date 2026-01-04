package com.example.app_fast_food.orderitem;

import org.mapstruct.Mapper;

import com.example.app_fast_food.orderitem.dto.OrderItemResponseDto;
import com.example.app_fast_food.orderitem.dto.ProductDto;
import com.example.app_fast_food.orderitem.entity.OrderItem;
import com.example.app_fast_food.product.entity.Product;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemResponseDto toResponseDTO(OrderItem orderItem);

    default ProductDto toProductDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        return dto;
    }
}
