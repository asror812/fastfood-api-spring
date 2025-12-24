package com.example.app_fast_food.product;

import com.example.app_fast_food.bonus.dto.bonus.BonusDto;
import com.example.app_fast_food.bonus.dto.bonus_condition.BonusConditionResponseDto;
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

    String BASE_DOWNLOAD_URL = "/attachments/";

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
            ProductImageResponseDto productImage = new ProductImageResponseDto(pi.getId(),
                    BASE_DOWNLOAD_URL + pi.getId(), pi.getPosition());
            images.add(productImage);
        }

        return images;
    }

    default List<BonusDto> toBonusDto(List<BonusProductLink> bpls) {
        List<BonusDto> bonuses = new ArrayList<>();

        for (BonusProductLink bpl : bpls) {
            Bonus bs = bpl.getBonus();
            BonusDto bonus = new BonusDto();

            bonus.setId(bs.getId());
            bonus.setName(bs.getName());
            bonus.setStartDate(bs.getStartDate());
            bonus.setEndDate(bs.getEndDate());
            bonus.setActive(bs.isActive());

            BonusConditionResponseDto bonusCondition = new BonusConditionResponseDto();
            bonusCondition.setId(bs.getId());
            bonusCondition.setValue(bs.getCondition().getValue());
            bonusCondition.setConditionType(bs.getCondition().getConditionType());

            bonus.setCondition(bonusCondition);
            bonuses.add(bonus);
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
