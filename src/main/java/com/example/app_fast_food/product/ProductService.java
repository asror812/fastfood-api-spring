package com.example.app_fast_food.product;

import com.example.app_fast_food.attachment.AttachmentMapper;
import com.example.app_fast_food.bonus.BonusMapper;
import com.example.app_fast_food.bonus.BonusRepository;
import com.example.app_fast_food.category.CategoryMapper;
import com.example.app_fast_food.common.mapper.BaseMapper;
import com.example.app_fast_food.common.service.GenericService;
import com.example.app_fast_food.discount.DiscountMapper;
import com.example.app_fast_food.discount.DiscountRepository;
import com.example.app_fast_food.exceptions.EntityNotFoundException;
import com.example.app_fast_food.product.dto.ProductCreateRequestDTO;
import com.example.app_fast_food.product.dto.ProductResponseDTO;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.review.ReviewMapper;
import com.example.app_fast_food.user.UserRepository;
import lombok.Getter;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Getter
public class ProductService
        extends GenericService<Product, ProductResponseDTO> {

    private final ProductRepository repository;
    private final Class<Product> entityClass = Product.class;
    private final ProductMapper mapper;
    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    private final BonusMapper bonusMapper;
    private final BonusRepository bonusRepository;
    private final UserRepository userRepository;
    private final CategoryMapper categoryMapper;
    private final ReviewMapper commentDTOMapper;
    private final AttachmentMapper attachmentDTOMapper;

    public ProductService(BaseMapper<Product, ProductResponseDTO> baseMapper, ProductRepository repository,
            ProductMapper mapper, DiscountRepository discountRepository, DiscountMapper discountMapper,
            BonusMapper bonusMapper, BonusRepository bonusRepository, UserRepository userRepository,
            CategoryMapper categoryMapper, ReviewMapper commentDTOMapper, AttachmentMapper attachmentDTOMapper) {
        super(baseMapper);

        this.repository = repository;
        this.mapper = mapper;
        this.discountRepository = discountRepository;
        this.discountMapper = discountMapper;
        this.bonusMapper = bonusMapper;
        this.bonusRepository = bonusRepository;
        this.userRepository = userRepository;
        this.categoryMapper = categoryMapper;
        this.commentDTOMapper = commentDTOMapper;
        this.attachmentDTOMapper = attachmentDTOMapper;
    }

    protected ProductResponseDTO create(ProductCreateRequestDTO productCreateDTO) {
        Product product = mapper.toEntity(productCreateDTO);
        repository.save(product);

        return mapper.toResponseDTO(product);
    }

    public List<ProductResponseDTO> getByCategory(String categoryName) {
        List<Product> products = repository.findProductsByCategoryName(categoryName);

        return getProductResponseDTOS(products);
    }

    public List<ProductResponseDTO> getCampaignProducts() {
        List<Product> campaignProducts = repository.getCampaignProducts();
        return getProductResponseDTOS(campaignProducts);
    }

    public ProductResponseDTO getSpecificProduct(UUID id) {
        Product product = repository.findProductById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id.toString()));

        return mapper.toResponseDTO(product);
    }

    public List<ProductResponseDTO> getProductResponseDTOS(List<Product> products) {

        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();

        for (Product popularProduct : products) {
            productResponseDTOS.add(mapper.toResponseDTO(popularProduct));
        }

        return productResponseDTOS;
    }

    public List<ProductResponseDTO> getDefaultProducts() {
        return repository.getPopularProducts().stream().map(p -> mapper.toResponseDTO(p)).toList();
    }
}