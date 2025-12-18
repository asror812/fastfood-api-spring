package com.example.app_fast_food.order;

import com.example.app_fast_food.bonus.BonusMapper;
import com.example.app_fast_food.discount.DiscountMapper;
import com.example.app_fast_food.order.dto.OrderCreateDto;
import com.example.app_fast_food.order.dto.OrderResponseDto;
import com.example.app_fast_food.order.dto.OrderUpdateDto;
import com.example.app_fast_food.order.entity.Order;
import com.example.app_fast_food.orderitem.OrderItemMapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = { DiscountMapper.class, BonusMapper.class, OrderItemMapper.class })
@Component
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "discounts", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "discountAmount", ignore = true)
    @Mapping(target = "finalPrice", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "appliedBonus", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Order toEntity(OrderCreateDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "discounts", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "discountAmount", ignore = true)
    @Mapping(target = "finalPrice", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void toEntity(OrderUpdateDto dto, @MappingTarget Order order);

    OrderResponseDto toResponseDto(Order order);

}
