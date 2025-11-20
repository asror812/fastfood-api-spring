package com.example.app_fast_food.discount;

import com.example.app_fast_food.common.mapper.BaseMapper;
import com.example.app_fast_food.common.service.GenericService;
import com.example.app_fast_food.discount.dto.DiscountResponseDTO;
import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.order.entity.Order;
import com.example.app_fast_food.orderItem.entity.OrderItem;
import com.example.app_fast_food.product.entity.Product;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Getter
public class DiscountService
        extends GenericService<Discount, DiscountResponseDTO> {

    private final DiscountRepository repository;
    private final Class<Discount> entityClass = Discount.class;
    private final DiscountMapper mapper;

    public DiscountService(BaseMapper<Discount, DiscountResponseDTO> baseMapper, DiscountRepository repository,
            DiscountMapper mapper) {
        super(baseMapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Discount> getActiveDiscountsWithoutRequirements() {
        return repository.getActiveHolidayDiscounts();
    }

    public Set<Discount> getActiveDiscountsWithProductQuantityRequirements(Order order) {
        Set<Discount> discounts = new HashSet<>();

        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            discounts.addAll(repository.findProductQuantityDiscounts(product, item.getQuantity()));
        }

        return discounts;
    }

}
