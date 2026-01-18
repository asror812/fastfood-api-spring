package com.example.app_fast_food.product;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.app_fast_food.bonus.BonusProductLinkRepository;
import com.example.app_fast_food.bonus.entity.BonusProductLink;
import com.example.app_fast_food.product.dto.ProductListResponseDto;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.productdiscount.ProductDiscount;
import com.example.app_fast_food.productdiscount.ProductDiscountReposity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductCacheService {
    private final ProductRepository repository;
    private final BonusProductLinkRepository bonusProductLinkRepository;
    private final ProductDiscountReposity productDiscountReposity;
    private final ProductMapper mapper;

    @Cacheable(CacheNames.POPULAR_PRODUCTS)
    public List<ProductResponseDto> getPopularProducts() {
        return repository.getPopularProducts().stream().map(mapper::toResponseDTO).toList();
    }

    @Cacheable(CacheNames.PRODUCTS)
    public List<ProductListResponseDto> getAll() {
        return repository.findAllProducts().stream().map(mapper::toListResponseDto).toList();
    }

    @Cacheable(value = CacheNames.PRODUCTS_BY_CATEGORY, key = "#p0")
    public List<ProductResponseDto> getAllByCategoryTree(UUID categoryId) {
        return repository.findProductsByCategoryTree(categoryId)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Cacheable(value = CacheNames.CAMPAIGN_PRODUCTS, key = "#p0")
    public List<ProductResponseDto> getCampaignProductsBase(LocalDate date) {
        List<Product> campaignProducts = repository.getCampaignProducts(date);
        if (campaignProducts.isEmpty())
            return List.of();

        List<UUID> productIds = campaignProducts.stream().map(Product::getId).toList();

        Map<UUID, List<BonusProductLink>> bonusesMap = bonusProductLinkRepository
                .findAllActiveByProductIds(productIds, LocalDate.now())
                .stream().collect(Collectors.groupingBy(b -> b.getProduct().getId()));

        Map<UUID, List<ProductDiscount>> discountsMap = productDiscountReposity
                .findAllActiveByProductIds(productIds, LocalDate.now())
                .stream().collect(Collectors.groupingBy(d -> d.getProduct().getId()));

        return campaignProducts.stream().map(p -> {
            List<BonusProductLink> pBonuses = bonusesMap.getOrDefault(p.getId(), List.of());
            List<ProductDiscount> pDiscounts = discountsMap.getOrDefault(p.getId(), List.of());

            return mapper.toFullResponse(p, pDiscounts, pBonuses);
        }).toList();
    }

}
