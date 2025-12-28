package com.example.app_fast_food.bonus;

import com.example.app_fast_food.bonus.dto.bonus.BonusCreateDto;
import com.example.app_fast_food.bonus.dto.bonus.BonusResponseDto;
import com.example.app_fast_food.bonus.dto.bonus.BonusUpdateRequestDto;
import com.example.app_fast_food.bonus.entity.Bonus;
import com.example.app_fast_food.bonus.entity.BonusProductLink;
import com.example.app_fast_food.product.dto.CategoryDto;
import com.example.app_fast_food.product.dto.ProductBonusDto;
import com.example.app_fast_food.product.dto.ProductDto;
import com.example.app_fast_food.product.entity.Product;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { BonusConditionMapper.class })
public interface BonusMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "condition", ignore = true)
    @Mapping(target = "bonusProductLinks", ignore = true)
    Bonus toEntity(BonusCreateDto dto);

    @Mapping(target = "products", source = "bonusProductLinks")
    BonusResponseDto toResponseDto(Bonus bonus);

    default List<ProductDto> toResponseDto(List<BonusProductLink> bonusProductLink) {
        List<ProductDto> products = new ArrayList<>();

        for (BonusProductLink bpl : bonusProductLink) {
            Product product = bpl.getProduct();
            com.example.app_fast_food.category.entity.Category category = product.getCategory();

            products.add(new ProductDto(product.getId(), product.getName(), product.getPrice(),
                    new CategoryDto(category.getId(), category.getName()), product.getWeight()));
        }

        return products;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bonusProductLinks", ignore = true)
    void toEntity(BonusUpdateRequestDto dto, @MappingTarget Bonus bonus);

    @Mapping(target = "id", source = "bonus.id")
    @Mapping(target = "name", source = "bonus.name")
    @Mapping(target = "startDate", source = "bonus.startDate")
    @Mapping(target = "endDate", source = "bonus.endDate")
    @Mapping(target = "condition", source = "bonus.condition")
    @Mapping(target = "usageLimit", source = "bonus.usageLimit")
    @Mapping(target = "active", source = "bonus.active")
    ProductBonusDto toBonusDto(BonusProductLink bpl);

}
