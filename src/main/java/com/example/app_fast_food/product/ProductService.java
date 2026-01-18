package com.example.app_fast_food.product;

import com.example.app_fast_food.bonus.BonusProductLinkRepository;
import com.example.app_fast_food.bonus.entity.BonusProductLink;
import com.example.app_fast_food.category.CategoryRepository;
import com.example.app_fast_food.exception.EntityNotFoundException;
import com.example.app_fast_food.favorite.FavoriteRepository;
import com.example.app_fast_food.product.dto.ProductCreateDto;
import com.example.app_fast_food.product.dto.ProductListResponseDto;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.productdiscount.ProductDiscount;
import com.example.app_fast_food.productdiscount.ProductDiscountReposity;
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
    private final CategoryRepository categoryRepository;
    private final BonusProductLinkRepository bonusProductLinkRepository;
    private final ProductDiscountReposity productDiscountReposity;

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
        if (!categoryRepository.existsById(categoryId))
            throw new EntityNotFoundException("Category", categoryId.toString());

        List<ProductResponseDto> all = cacheService.getAllByCategoryTree(categoryId);

        if (auth == null)
            return all;

        Set<UUID> favoriteIds = favoriteRepository.findAllProductIdsByUserId(auth.getId());

        return all.stream()
                .map(p -> copyWithFavorite(p, favoriteIds.contains(p.getId())))
                .toList();
    }

    public List<ProductResponseDto> getCampaignProducts(AuthDto auth) {
        LocalDate now = LocalDate.now();
        List<ProductResponseDto> all = cacheService.getCampaignProductsBase(now);

        if (auth == null)
            return all;

        Set<UUID> favoriteIds = favoriteIdsOrEmpty(auth);

        return all.stream()
                .map(p -> copyWithFavorite(p, favoriteIds.contains(p.getId())))
                .toList();
    }

    public List<ProductListResponseDto> getAll(AuthDto auth) {
        List<ProductListResponseDto> all = cacheService.getAll();

        if (auth == null)
            return all;

        Set<UUID> favoriteIds = favoriteIdsOrEmpty(auth);

        return all.stream()
                .map(p -> copyListWithFavorite(p, favoriteIds.contains(p.getId())))
                .toList();
    }

    public List<ProductResponseDto> getPopularProducts(AuthDto auth) {
        List<ProductResponseDto> all = cacheService.getPopularProducts();

        if (auth == null)
            return all;

        Set<UUID> favoriteIds = favoriteIdsOrEmpty(auth);

        return all.stream()
                .map(p -> copyWithFavorite(p, favoriteIds.contains(p.getId())))
                .toList();
    }

    public ProductResponseDto getById(UUID id) {
        Product product = repository.findProductById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id.toString()));

        List<BonusProductLink> bonuses = bonusProductLinkRepository.findActiveByProductId(id, LocalDate.now());
        List<ProductDiscount> discounts = productDiscountReposity.findActiveByProductId(id, LocalDate.now());

        return mapper.toFullResponse(product, discounts, bonuses);
    }

    private Set<UUID> favoriteIdsOrEmpty(AuthDto auth) {
        return (auth == null)
                ? Set.of()
                : favoriteRepository.findAllProductIdsByUserId(auth.getId());
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

    private ProductListResponseDto copyListWithFavorite(ProductListResponseDto p, boolean favorite) {
        ProductListResponseDto c = new ProductListResponseDto();
        c.setId(p.getId());
        c.setName(p.getName());
        c.setPrice(p.getPrice());
        c.setCategory(p.getCategory());
        c.setWeight(p.getWeight());
        c.setImages(p.getImages());
        c.setFavorite(favorite);

        return c;
    }

}
