package com.example.app_fast_food.product;

import com.example.app_fast_food.bonus.dto.bonus.BonusResponseDto;
import com.example.app_fast_food.bonus.entity.Bonus;
import com.example.app_fast_food.bonus.entity.BonusProductLink;
import com.example.app_fast_food.category.CategoryMapper;
import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.product.dto.ProductCreateDto;
import com.example.app_fast_food.product.dto.ProductDiscountResponseDto;
import com.example.app_fast_food.product.dto.ProductImageResponseDto;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.productdiscount.ProductDiscount;
import com.example.app_fast_food.productdiscount.ProductDiscountMapper;
import com.example.app_fast_food.productimage.ProductImage;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { CategoryMapper.class, ProductDiscountMapper.class })
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "bonuses", ignore = true)
    @Mapping(target = "discounts", ignore = true)
    @Mapping(target = "images", ignore = true)
    public Product toEntity(ProductCreateDto productCreateDto);

    public ProductResponseDto toResponseDTO(Product product);

    default List<ProductImageResponseDto> toProductImageResponseDto(List<ProductImage> pis) {
        List<ProductImageResponseDto> images = new ArrayList<>();
        for (ProductImage pi : pis) {
            ProductImageResponseDto productImage = new ProductImageResponseDto(pi.getId(), pi.getPosition(),
                    pi.getAttachment().getDownloadUrl());
            images.add(productImage);
        }

        return images;
    }

    default List<BonusResponseDto> toBonusResponseDto(List<BonusProductLink> bpls) {
        List<BonusResponseDto> bonuses = new ArrayList<>();
        for (BonusProductLink bpl : bpls) {
            Bonus bs = bpl.getBonus();

            BonusResponseDto bonus = new BonusResponseDto();

            bonus.setActive(bs.isActive());
            bonus.setEndDate(bs.getEndDate());
            bonus.setId(bs.getId());
            bonus.setName(bs.getName());
            bonus.setStartDate(bs.getStartDate());

            bonuses.add(new BonusResponseDto());
        }
        return bonuses;
    }

    default List<ProductDiscountResponseDto> toProductResponseDto(List<ProductDiscount> pds) {
        List<ProductDiscountResponseDto> productDiscounts = new ArrayList<>();

        for (ProductDiscount pd : pds) {
            Discount discount = pd.getDiscount();
            ProductDiscountResponseDto productDiscount = new ProductDiscountResponseDto(pd.getId(),
                    pd.getDiscount().getName(), discount.getPercentage(), discount.getStartDate(),
                    discount.getEndDate(), discount.getRequiredQuantity());

            productDiscounts.add(productDiscount);
        }

        return productDiscounts;
    }

}
