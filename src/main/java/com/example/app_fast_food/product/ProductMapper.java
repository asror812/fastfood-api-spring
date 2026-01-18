package com.example.app_fast_food.product;

import com.example.app_fast_food.bonus.BonusMapper;
import com.example.app_fast_food.bonus.entity.Bonus;
import com.example.app_fast_food.bonus.entity.BonusCondition;
import com.example.app_fast_food.bonus.entity.BonusProductLink;
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
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = { CategoryMapper.class, ProductDiscountMapper.class, BonusMapper.class })
public interface ProductMapper {

    String BASE_DOWNLOAD_URL = "/attachments/download/";

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "bonuses", ignore = true)
    @Mapping(target = "discounts", ignore = true)
    @Mapping(target = "images", ignore = true)
    Product toEntity(ProductCreateDto productCreateDto);

    @Mapping(target = "images", source = "images")
    @Mapping(target = "bonuses", source = "bonuses")
    @Mapping(target = "discounts", source = "discounts")
    @Mapping(target = "favorite", ignore = true)
    ProductResponseDto toResponseDTO(Product product);

    @Mapping(target = "favorite", ignore = true)
    ProductListResponseDto toListResponseDto(Product product);

    default ProductResponseDto toFullResponse(
            Product product,
            List<ProductDiscount> discounts,
            List<BonusProductLink> bonuses) {
        if (product == null)
            return null;

        ProductResponseDto dto = toResponseDTO(product);

        dto.setDiscounts(toProductDiscountDto(discounts));
        dto.setBonuses(toBonusDto(bonuses));

        return dto;
    }

    default List<ProductBonusDto> toBonusDto(List<BonusProductLink> bpls) {
        if (bpls == null || bpls.isEmpty())
            return Collections.emptyList();

        List<ProductBonusDto> productBonuses = new ArrayList<>();

        for (BonusProductLink bpl : bpls) {
            Bonus bonus = bpl.getBonus();
            if (bonus == null)
                continue;

            productBonuses.add(new ProductBonusDto(
                    bpl.getId(),
                    bonus.getName(),
                    bonus.getStartDate(),
                    bonus.getEndDate(),
                    toBonusConditionDto(bonus.getCondition()),
                    bonus.getUsageLimit()));
        }

        return productBonuses;
    }

    default BonusConditionDto toBonusConditionDto(BonusCondition condition) {
        return new BonusConditionDto(condition.getId(), condition.getConditionType(), condition.getValue());
    }

    default List<ProductDiscountDto> toProductDiscountDto(List<ProductDiscount> pds) {
        if (pds == null || pds.isEmpty())
            return Collections.emptyList();

        List<ProductDiscountDto> productDiscounts = new ArrayList<>();

        for (ProductDiscount pd : pds) {
            Discount discount = pd.getDiscount();
            if (discount == null)
                continue;

            productDiscounts.add(new ProductDiscountDto(
                    pd.getId(),
                    discount.getName(),
                    discount.getPercentage(),
                    discount.getRequiredQuantity()));
        }

        return productDiscounts;
    }

    default List<ProductImageResponseDto> toProductImageResponseDto(List<ProductImage> pis) {
        if (pis == null || pis.isEmpty())
            return Collections.emptyList();

        List<ProductImageResponseDto> productImages = new ArrayList<>();

        for (ProductImage pi : pis) {
            if (pi == null || pi.getAttachment() == null) {
                continue;
            }

            productImages.add(
                    new ProductImageResponseDto(pi.getId(), BASE_DOWNLOAD_URL + pi.getAttachment().getId(),
                            pi.getPosition()));
        }
        return productImages;
    }

}
