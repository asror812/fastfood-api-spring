package com.example.app_fast_food.orderItem;

import com.example.app_fast_food.common.mapper.BaseMapper;
import com.example.app_fast_food.common.service.GenericService;
import com.example.app_fast_food.orderItem.dto.OrderItemResponseDTO;
import com.example.app_fast_food.orderItem.entity.OrderItem;

import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Getter
public class OrderItemService
        extends GenericService<OrderItem, OrderItemResponseDTO> {

    private final OrderItemRepository repository;
    private final Class<OrderItem> entityClass = OrderItem.class;
    private final OrderItemMapper mapper;

    public OrderItemService(BaseMapper<OrderItem, OrderItemResponseDTO> baseMapper, OrderItemRepository repository,
            OrderItemMapper mapper2) {
        super(baseMapper);

        this.repository = repository;
        mapper = mapper2;
    }

    public List<OrderItemResponseDTO> getResponseDTOS(List<OrderItem> orderItems) {
        return orderItems.stream().map(mapper::toResponseDTO)
                .toList();
    }

}
