package com.example.app_fast_food.bonus.entity;

import java.util.UUID;

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
@Table(name = "bonus_product_links")
@Getter
@Setter
@NoArgsConstructor
public class BonusProductLink {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bonus_id", nullable = false)
    private Bonus bonus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity = 1;

    public BonusProductLink(Bonus bonus, Product product, int quantity) {
        this.bonus = bonus;
        this.product = product;
        this.quantity = quantity;
    }
}
