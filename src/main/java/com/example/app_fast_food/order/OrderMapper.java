package com.example.app_fast_food.order;

import com.example.app_fast_food.bonus.BonusMapper;
import com.example.app_fast_food.common.mapper.BaseMapper;
import com.example.app_fast_food.discount.DiscountMapper;
import com.example.app_fast_food.order.dto.OrderCreateRequestDTO;
import com.example.app_fast_food.order.dto.OrderResponseDTO;
import com.example.app_fast_food.order.dto.OrderUpdateRequestDTO;
import com.example.app_fast_food.order.entity.Order;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = { DiscountMapper.class, BonusMapper.class })
@Component
public interface OrderMapper extends BaseMapper<Order, OrderResponseDTO> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "discounts", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "discountAmount", ignore = true)
    @Mapping(target = "finalPrice", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "appliedBonus", ignore = true)
    Order toEntity(OrderCreateRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "discounts", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "discountAmount", ignore = true)
    @Mapping(target = "finalPrice", ignore = true)
    void toEntity(OrderUpdateRequestDTO dto, @MappingTarget Order order);
}
