package com.example.app_fast_food.product.entity;

import com.example.app_fast_food.attachment.entity.Attachment;
import com.example.app_fast_food.bonus.entity.BonusProductLink;
import com.example.app_fast_food.category.entity.Category;
import com.example.app_fast_food.product_discounts.entity.ProductDiscount;

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

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private int weight;

    @OneToMany(mappedBy = "product")
    private List<ProductDiscount> productDiscounts = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "main_image")
    private Attachment mainImage;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "secondary_image")
    private Attachment secondaryImage;

    @OneToMany(mappedBy = "product")
    private List<BonusProductLink> bonusProductLinks = new ArrayList<>();

    public Product(UUID id, String name, BigDecimal price, Category category, int weight, Attachment main,
            Attachment other) {
        this.id = id;
        this.name = name;
        this.price = price;

        this.category = category;
        this.weight = weight;
        this.mainImage = main;
        this.secondaryImage = other;
    }

}
