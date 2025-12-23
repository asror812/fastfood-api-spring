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
import java.util.ArrayList;
import java.util.List;
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private int weight;

    @OneToMany(mappedBy = "product")
    private List<ProductDiscount> discounts = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<BonusProductLink> bonuses = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<ProductImage> images = new ArrayList<>();

    public Product(UUID id, String name, BigDecimal price, Category category, int weight) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.weight = weight;
    }
}
