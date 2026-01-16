package com.example.app_fast_food.productimage;

import java.util.UUID;

import org.hibernate.annotations.Check;

import com.example.app_fast_food.attachment.entity.Attachment;
import com.example.app_fast_food.product.entity.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Check(constraints = "position >= 0")
@Table(name = "product_images", uniqueConstraints = @UniqueConstraint(columnNames = { "product_id",
        "position" }), indexes = @Index(name = "idx_product_images_product", columnList = "product_id"))
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attachment_id", nullable = false)
    private Attachment attachment;

    @Column(nullable = false)
    private int position;

    public ProductImage(Product product, Attachment attachment, int position) {
        this.product = product;
        this.attachment = attachment;
        this.position = position;
    }
}
