package com.example.app_fast_food.product.entity;

import com.example.app_fast_food.bonus.entity.BonusProductLink;
import com.example.app_fast_food.category.entity.Category;
import com.example.app_fast_food.productdiscount.ProductDiscount;
import com.example.app_fast_food.productimage.ProductImage;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private int weight;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductDiscount> discounts = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<BonusProductLink> bonuses = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImage> images = new HashSet<>();

    public Product(UUID id, String name, BigDecimal price, Category category, int weight) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.weight = weight;
    }
}
