package com.example.app_fast_food.discount;

import com.example.app_fast_food.bonus.dto.bonus.CategoryDto;
import com.example.app_fast_food.discount.dto.DiscountResponseDto;
import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.product.dto.ProductDto;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.productdiscount.ProductDiscount;
import com.example.app_fast_food.category.entity.Category;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountMapper {

    public DiscountResponseDto toResponseDTO(Discount discount);

    default List<ProductDto> toProductDto(List<ProductDiscount> productDiscounts) {
        List<ProductDto> products = new ArrayList<>();

        for (ProductDiscount bd : productDiscounts) {
            Product product = bd.getProduct();
            Category category = product.getCategory();

            products.add(new ProductDto(product.getId(), product.getName(), product.getPrice(),
                    new CategoryDto(category.getId(), category.getName()), product.getWeight()));
        }

        return products;
    }
}