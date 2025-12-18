package com.example.app_fast_food.discount;

import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.order.entity.Order;
import com.example.app_fast_food.orderitem.entity.OrderItem;
import com.example.app_fast_food.product.entity.Product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Getter
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository repository;
    private final DiscountMapper mapper;

    public List<Discount> getActiveDiscountsWithoutRequirements() {
        return repository.getActiveHolidayDiscounts();
    }

    public Set<Discount> getActiveDiscountsWithProductQuantityRequirements(Order order) {
        Set<Discount> discounts = new HashSet<>();

        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            discounts.addAll(repository.findProductQuantityDiscounts(product.getId(), item.getQuantity()));
        }

        return discounts;
    }

}
