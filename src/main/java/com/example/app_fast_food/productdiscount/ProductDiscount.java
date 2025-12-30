package com.example.app_fast_food.productdiscount;

import java.util.UUID;

import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.product.entity.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_discounts")
@Getter
@Setter
@NoArgsConstructor
public class ProductDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    public ProductDiscount(Product product, Discount discount) {
        this.product = product;
        this.discount = discount;
    }
    
}
