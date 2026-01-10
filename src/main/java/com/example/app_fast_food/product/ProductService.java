package com.example.app_fast_food.product;

import com.example.app_fast_food.favorite.FavoriteRepository;
import com.example.app_fast_food.product.dto.ProductCreateDto;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.user.dto.AuthDto;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper mapper;

    private final FavoriteRepository favoriteRepository;
    private final ProductRepository repository;

    private final ProductCacheService cacheService;

    @CacheEvict(value = {
            CacheNames.CAMPAIGN_PRODUCTS,
            CacheNames.POPULAR_PRODUCTS,
            CacheNames.PRODUCTS,
            CacheNames.PRODUCTS_BY_CATEGORY
    }, allEntries = true)
    @Transactional
    public ProductResponseDto create(ProductCreateDto createDto) {
        Product product = mapper.toEntity(createDto);
        repository.save(product);

        return mapper.toResponseDTO(product);
    }

    public List<ProductResponseDto> getAllByCategory(UUID categoryId, AuthDto auth) {
        List<ProductResponseDto> products = cacheService.getAllByCategoryTree(categoryId);

        Set<UUID> favoriteIds = favoriteRepository.findAllProductIdsByUserId(auth.getId());

        return products.stream()
                .map(p -> copyWithFavorite(p, favoriteIds.contains(p.getId()))).toList();
    }

    public List<ProductResponseDto> getCampaignProducts(AuthDto auth) {
        LocalDate now = LocalDate.now();

        List<ProductResponseDto> base = cacheService.getCampaignProductsBase(now);

        var favSet = favoriteRepository.findAllProductIdsByUserId(auth.getId());

        return base.stream()
                .map(p -> copyWithFavorite(p, favSet.contains(p.getId())))
                .toList();
    }

    private ProductResponseDto copyWithFavorite(ProductResponseDto p, boolean favorite) {
        ProductResponseDto c = new ProductResponseDto();
        c.setId(p.getId());
        c.setName(p.getName());
        c.setPrice(p.getPrice());
        c.setCategory(p.getCategory());
        c.setWeight(p.getWeight());
        c.setImages(p.getImages());
        c.setBonuses(p.getBonuses());
        c.setDiscounts(p.getDiscounts());
        c.setFavorite(favorite);

        return c;
    }

}
