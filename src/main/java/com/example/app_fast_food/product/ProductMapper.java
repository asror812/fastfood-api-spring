package com.example.app_fast_food.product;

import com.example.app_fast_food.bonus.BonusMapper;
import com.example.app_fast_food.category.CategoryMapper;
import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.product.dto.*;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.productdiscount.ProductDiscount;
import com.example.app_fast_food.productdiscount.ProductDiscountMapper;
import com.example.app_fast_food.productimage.ProductImage;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = { CategoryMapper.class, ProductDiscountMapper.class, BonusMapper.class })
public interface ProductMapper {

    String BASE_DOWNLOAD_URL = "/attachments/";

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "bonuses", ignore = true)
    @Mapping(target = "discounts", ignore = true)
    @Mapping(target = "images", ignore = true)
    Product toEntity(ProductCreateDto productCreateDto);

    @Mapping(target = "images", source = "images")
    @Mapping(target = "bonuses", source = "bonuses")
    @Mapping(target = "discounts", source = "discounts")
    ProductResponseDto toResponseDTO(Product product);

    default Set<ProductDiscountResponseDto> toProductDiscountResponseDto(Set<ProductDiscount> pds) {
        Set<ProductDiscountResponseDto> productDiscounts = new HashSet<>();
        if (pds == null)
            return productDiscounts;

        for (ProductDiscount pd : pds) {
            Discount discount = pd.getDiscount();
            if (discount == null)
                continue;

            productDiscounts.add(new ProductDiscountResponseDto(
                    pd.getId(),
                    discount.getName(),
                    discount.getPercentage(),
                    discount.getStartDate(),
                    discount.getEndDate(),
                    discount.getRequiredQuantity()));
        }
        return productDiscounts;
    }

    default List<ProductImageResponseDto> toProductDiscountResponseDto(List<ProductImage> pis) {
        List<ProductImageResponseDto> productImages = new ArrayList<>();
        if (pis == null)
            return productImages;

        for (ProductImage pi : pis) {
            if (pi == null || pi.getAttachment() == null) {
                continue;
            }

            productImages.add(
                    new ProductImageResponseDto(pi.getId(), BASE_DOWNLOAD_URL + pi.getAttachment().getStoredName(),
                            pi.getPosition()));
        }
        return productImages;
    }

}
