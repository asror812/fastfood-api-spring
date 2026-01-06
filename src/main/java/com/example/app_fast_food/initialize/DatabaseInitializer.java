package com.example.app_fast_food.initialize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.app_fast_food.attachment.AttachmentRepository;
import com.example.app_fast_food.attachment.entity.Attachment;
import com.example.app_fast_food.bonus.BonusConditionRepository;
import com.example.app_fast_food.bonus.BonusRepository;
import com.example.app_fast_food.bonus.entity.Bonus;
import com.example.app_fast_food.bonus.entity.BonusCondition;
import com.example.app_fast_food.bonus.entity.BonusProductLink;
import com.example.app_fast_food.bonus.entity.ConditionType;
import com.example.app_fast_food.category.CategoryRepository;
import com.example.app_fast_food.category.entity.Category;
import com.example.app_fast_food.discount.DiscountRepository;
import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.discount.entity.DiscountType;
import com.example.app_fast_food.exception.InvalidImageTypeException;
import com.example.app_fast_food.order.OrderRepository;
import com.example.app_fast_food.order.entity.Order;
import com.example.app_fast_food.order.entity.OrderStatus;
import com.example.app_fast_food.order.entity.PaymentType;
import com.example.app_fast_food.orderitem.entity.OrderItem;
import com.example.app_fast_food.product.ProductRepository;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.productdiscount.ProductDiscount;
import com.example.app_fast_food.productdiscount.ProductDiscountReposity;
import com.example.app_fast_food.productimage.ProductImage;
import com.example.app_fast_food.user.UserRepository;
import com.example.app_fast_food.user.entity.AdminProfile;
import com.example.app_fast_food.user.entity.CustomerProfile;
import com.example.app_fast_food.user.entity.User;
import com.example.app_fast_food.user.permission.Permission;
import com.example.app_fast_food.user.permission.PermissionRepository;
import com.example.app_fast_food.user.role.Role;
import com.example.app_fast_food.user.role.RoleRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

        @Value("${api.images.base.download-url}")
        private String baseDownloadUrl;

        @Value("${api.admin.password}")
        private String adminPassword;

        @Value("${api.admin.name}")
        private String adminName;

        @Value("${api.admin.phoneNumber}")
        private String adminPhoneNumber;

        private final PasswordEncoder passwordEncoder;

        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PermissionRepository permissionRepository;
        private final ProductRepository productRepository;
        private final DiscountRepository discountRepository;
        private final ProductDiscountReposity productDiscountReposity;
        private final CategoryRepository categoryRepository;
        private final BonusRepository bonusRepository;
        private final BonusConditionRepository bonusConditionRepository;
        private final OrderRepository orderRepository;
        private final AttachmentRepository attachmentRepository;

        private static final String BEEF_LAVASH_WITH_CHEESE = "Beef Lavash with Cheese";
        private static final String CLASSIC_BEEF_BURGER_NAME = "Classic Beef Burger";

        private static final String ADMIN = "ADMIN";
        private static final String CUSTOMER = "CUSTOMER";

        private User adminUser;

        @Override
        public void run(String... args) {

                createPermissions();
                createRoles();
                createUsers();

                createDiscounts();
                createCategoriesAndProducts();

                createBonuses();
                createOrders();
        }

        // BONUSES
        private void createOrders() {
                if (orderRepository.count() > 0)
                        return;

                Product beefLavashWithCheese = productRepository.findByName(BEEF_LAVASH_WITH_CHEESE)
                                .orElseThrow(() -> new RuntimeException("Beef Lavash with Cheese product not found"));

                Product classicBeefBurger = productRepository.findByName(CLASSIC_BEEF_BURGER_NAME)
                                .orElseThrow(() -> new RuntimeException("Classic Beef Burger product not found"));

                Order order = new Order(OrderStatus.TAKEN, PaymentType.CASH, adminUser);

                BigDecimal totalPrice1 = beefLavashWithCheese.getPrice().multiply(BigDecimal.valueOf(4));
                OrderItem orderItem1 = new OrderItem(beefLavashWithCheese.getPrice(), 4, totalPrice1, BigDecimal.ZERO,
                                totalPrice1, beefLavashWithCheese, order);

                BigDecimal totalPrice2 = classicBeefBurger.getPrice().multiply(BigDecimal.valueOf(1));
                OrderItem orderItem2 = new OrderItem(classicBeefBurger.getPrice(), 1, totalPrice2, BigDecimal.ZERO,
                                totalPrice2, classicBeefBurger, order);

                order.getOrderItems().addAll(List.of(orderItem1, orderItem2));

                order.setAppliedBonus(false);

                order.setDiscountAmount(BigDecimal.ZERO);
                order.setTotalPrice(totalPrice1.add(totalPrice2));
                order.setFinalPrice(order.getTotalPrice());

                orderRepository.save(order);
        }

        @Transactional
        private void createBonuses() {
                if (bonusRepository.count() > 0)
                        return;

                BonusCondition holidayCondition = new BonusCondition(null, ConditionType.HOLIDAY_BONUS, "1");
                BonusCondition birthdayCondition = new BonusCondition(null, ConditionType.USER_BIRTHDAY, "1");
                BonusCondition quantityCondition = new BonusCondition(null, ConditionType.QUANTITY, "2");
                BonusCondition firstPurchaseCondition = new BonusCondition(null, ConditionType.FIRST_PURCHASE, "1");
                BonusCondition totalPriceCondition = new BonusCondition(null, ConditionType.TOTAL_PRICE, "150000");

                bonusConditionRepository.saveAll(List.of(
                                holidayCondition,
                                birthdayCondition,
                                quantityCondition,
                                firstPurchaseCondition,
                                totalPriceCondition));

                Product classicBeefBurger = mustFindProduct(CLASSIC_BEEF_BURGER_NAME);
                Product beefLavashWithCheese = mustFindProduct(BEEF_LAVASH_WITH_CHEESE);
                Product pepperoniLarge = mustFindProduct("Pepperoni Large");
                Product pepperoniMedium = mustFindProduct("Pepperoni Medium");
                Product cocaCola = mustFindProduct("Coca Cola 0.5L");
                Product sprite = mustFindProduct("Sprite 0.5L");
                Product vanillaIceCream = mustFindProduct("Vanilla Ice Cream Cup");
                Product chocolateIceCream = mustFindProduct("Chocolate Ice Cream Cup");

                Bonus birthdayBonus = new Bonus(
                                "Birthday Bonus",
                                birthdayCondition,
                                1,
                                LocalDate.now(),
                                LocalDate.now().plusMonths(12),
                                true);

                Bonus navruzHolidayBonus = new Bonus(
                                "Navruz Holiday Bonus",
                                holidayCondition,
                                1,
                                LocalDate.now(),
                                LocalDate.now().plusWeeks(3),
                                true);

                Bonus quantityBonus = new Bonus(
                                "Buy 2 Get Bonus",
                                quantityCondition,
                                1,
                                LocalDate.now(),
                                LocalDate.now().plusMonths(2),
                                true);

                Bonus firstPurchaseBonus = new Bonus(
                                "First Purchase Bonus",
                                firstPurchaseCondition,
                                1,
                                LocalDate.now(),
                                LocalDate.now().plusMonths(6),
                                true);

                Bonus totalPriceBonus = new Bonus(
                                "150k Order Bonus",
                                totalPriceCondition,
                                1,
                                LocalDate.now(),
                                LocalDate.now().plusMonths(1),
                                true);

                bonusRepository.saveAll(List.of(
                                birthdayBonus,
                                navruzHolidayBonus,
                                quantityBonus,
                                firstPurchaseBonus,
                                totalPriceBonus));

                // 4) BONUS PRODUCT LINKS
                // Birthday
                addLinks(birthdayBonus,
                                link(birthdayBonus, classicBeefBurger, 1),
                                link(birthdayBonus, vanillaIceCream, 1));

                // Holiday
                addLinks(navruzHolidayBonus,
                                link(navruzHolidayBonus, beefLavashWithCheese, 1),
                                link(navruzHolidayBonus, pepperoniLarge, 1));

                // Quantity
                addLinks(quantityBonus,
                                link(quantityBonus, pepperoniMedium, 1),
                                link(quantityBonus, cocaCola, 2));

                // First purchase
                addLinks(firstPurchaseBonus,
                                link(firstPurchaseBonus, sprite, 1),
                                link(firstPurchaseBonus, chocolateIceCream, 1));

                // Total price
                addLinks(totalPriceBonus,
                                link(totalPriceBonus, pepperoniLarge, 1),
                                link(totalPriceBonus, beefLavashWithCheese, 1));

                bonusRepository.saveAll(List.of(birthdayBonus, navruzHolidayBonus, quantityBonus, firstPurchaseBonus,
                                totalPriceBonus));
        }

        private Product mustFindProduct(String name) {
                return productRepository.findByName(name)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Product with name `%s` not found".formatted(name)));
        }

        private BonusProductLink link(Bonus bonus, Product product, int qty) {
                return new BonusProductLink(null, bonus, product, qty);
        }

        private void addLinks(Bonus bonus, BonusProductLink... links) {
                List<BonusProductLink> list = Arrays.asList(links);

                bonus.getBonusProductLinks().addAll(list);
        }

        @Transactional
        public void createCategoriesAndProducts() {
                if (categoryRepository.count() > 0 || productRepository.count() > 0) {
                        return;
                }
                // ================= CATEGORIES =================
                Category burgers = new Category("Burgers & Sandwiches", null);
                Category lavash = new Category("Rolls & Lavash", null);
                Category doner = new Category("Doners", null);
                Category pizza = new Category("Pizza", null);
                Category drinks = new Category("Drinks", null);
                Category desserts = new Category("Desserts", null);

                Category beefBurger = new Category("Beef Burgers", burgers);
                Category beefLavash = new Category("Beef Lavash", lavash);
                Category cheeseLavash = new Category("Cheese Lavash", lavash);
                Category beefDoner = new Category("Beef Doner", doner);
                Category pepperoniPizza = new Category("Pepperoni Pizza", pizza);
                Category coldDrinks = new Category("Cold Drinks", drinks);
                Category iceCream = new Category("Ice Cream", desserts);
                Category donuts = new Category("Donuts", desserts);

                categoryRepository.saveAll(
                                List.of(
                                                burgers, lavash, doner, pizza, drinks, desserts,
                                                beefBurger, beefLavash, cheeseLavash, beefDoner,
                                                pepperoniPizza, coldDrinks, iceCream, donuts));

                // ================= DISCOUNTS =================
                Discount extra1 = discountRepository.findByNameWithProducts("Extra-1")
                                .orElseThrow(() -> new RuntimeException("Discount Extra not found"));

                Discount extra2 = discountRepository.findByNameWithProducts("Extra-2")
                                .orElseThrow(() -> new RuntimeException("Discount Extra-2 not found"));

                // ================= PRODUCTS =================
                Product classicBeefBurger = new Product(null, CLASSIC_BEEF_BURGER_NAME,
                                new BigDecimal(23000), beefBurger, 280);
                addProductImage(classicBeefBurger, "burger_1", "jpg", 1);
                addProductImage(classicBeefBurger, "burger_2", "jpg", 2);

                Product cheeseBurger = new Product(null, "Cheeseburger",
                                new BigDecimal(30000), beefBurger, 380);
                addProductImage(cheeseBurger, "cheese_burger_1", "jpg", 1);
                addProductImage(cheeseBurger, "cheese_burger_2", "jpg", 2);

                Product classicBeefLavash = new Product(null, "Classic Beef Lavash",
                                new BigDecimal(23000), beefLavash, 280);
                addProductImage(classicBeefLavash, "beef_lavash_1", "webp", 1);
                addProductImage(classicBeefLavash, "beef_lavash_2", "webp", 2);

                Product beefLavashWithCheese = new Product(null, BEEF_LAVASH_WITH_CHEESE,
                                new BigDecimal(30000), beefLavash, 380);
                addProductImage(beefLavashWithCheese, "beef_lavash_with_cheese_1", "jpg", 1);
                addProductImage(beefLavashWithCheese, "beefe_lavash_with_cheese_2", "jpg", 2);

                Product pepperoniMedium = new Product(null, "Pepperoni Medium",
                                new BigDecimal(23000), pepperoniPizza, 280);
                addProductImage(pepperoniMedium, "pepperoni_pizza_1", "jpeg", 1);
                addProductImage(pepperoniMedium, "pepperoni_pizza_2", "jpeg", 2);

                Product pepperoniLarge = new Product(null, "Pepperoni Large",
                                new BigDecimal(30000), pepperoniPizza, 380);
                addProductImage(pepperoniLarge, "large_pepperoni_pizza_1", "webp", 1);
                addProductImage(pepperoniLarge, "large_pepperoni_pizza_2", "jpg", 2);

                Product vanillaIceCream = new Product(null, "Vanilla Ice Cream Cup",
                                new BigDecimal(20_000), iceCream, 250);
                addProductImage(vanillaIceCream, "vanilla_ice_cream_1", "jpg", 1);
                addProductImage(vanillaIceCream, "vanilla_ice_cream_2", "jpeg", 2);

                Product chocolateIceCream = new Product(null, "Chocolate Ice Cream Cup",
                                new BigDecimal(20_000), iceCream, 250);
                addProductImage(chocolateIceCream, "chocolate_ice_cream_1", "jpg", 1);
                addProductImage(chocolateIceCream, "chocolate_ice_cream_2", "jpg", 2);

                Product sprite = new Product(null, "Sprite 0.5L",
                                new BigDecimal(25_000), coldDrinks, 300);
                addProductImage(sprite, "sprite_0.5_1", "png", 1);
                addProductImage(sprite, "sprite_0.5_2", "png", 2);

                Product cocaCola = new Product(null, "Coca Cola 0.5L",
                                new BigDecimal(10_000), coldDrinks, 500);
                addProductImage(cocaCola, "cocacola_0.5_1", "avif", 1);
                addProductImage(cocaCola, "cocacola_0.5_2", "avif", 2);

                List<Product> products = List.of(
                                classicBeefBurger, cheeseBurger,
                                classicBeefLavash, beefLavashWithCheese,
                                pepperoniMedium, pepperoniLarge,
                                vanillaIceCream, chocolateIceCream,
                                sprite, cocaCola);

                productRepository.saveAll(products);

                // ================= PRODUCT DISCOUNTS =================
                productDiscountReposity.saveAll(List.of(
                                new ProductDiscount(pepperoniLarge, extra1),
                                new ProductDiscount(pepperoniLarge, extra2),
                                new ProductDiscount(chocolateIceCream, extra1),
                                new ProductDiscount(vanillaIceCream, extra1),
                                new ProductDiscount(classicBeefBurger, extra2)));
        }

        private void addProductImage(Product product, String baseName, String type, int position) {
                Attachment attachment = createAttachment(baseName, type);

                attachmentRepository.save(attachment);
                ProductImage image = new ProductImage(null, product, attachment, position);
                product.getImages().add(image);
        }

        @Transactional
        private void createDiscounts() {
                if (discountRepository.count() > 0)
                        return;

                Discount discount1 = new Discount(
                                "Navruz",
                                DiscountType.HOLIDAY,
                                5,
                                null,
                                null,
                                LocalDate.now(),
                                LocalDate.now().plusDays(10),
                                true);

                Discount discount2 = new Discount(
                                "Extra-1",
                                DiscountType.PRODUCT_QUANTITY,
                                10,
                                3,
                                null,
                                LocalDate.now(),
                                LocalDate.now().plusDays(10),
                                true);

                Discount discount3 = new Discount(
                                "Extra-2",
                                DiscountType.PRODUCT_QUANTITY,
                                15,
                                2,
                                null,
                                LocalDate.now(),
                                LocalDate.now().plusDays(5),
                                true);

                List<Discount> discounts = new ArrayList<>(List.of(discount1, discount2, discount3));
                discountRepository.saveAll(discounts);
        }

        @Transactional
        private void createUsers() {
                if (userRepository.findAll().isEmpty()) {
                        Role adminRole = roleRepository.findByName(ADMIN)
                                        .orElseThrow(() -> new com.example.app_fast_food.exception.EntityNotFoundException(
                                                        "Role", ADMIN));

                        Role customerRole = roleRepository.findByName(CUSTOMER)
                                        .orElseThrow(() -> new com.example.app_fast_food.exception.EntityNotFoundException(
                                                        "Role", CUSTOMER));

                        adminUser = new User(
                                        adminPhoneNumber,
                                        adminName,
                                        passwordEncoder.encode(adminPassword),
                                        LocalDate.now());

                        AdminProfile adminProfile = new AdminProfile();
                        adminProfile.setUser(adminUser);

                        CustomerProfile customerProfile = new CustomerProfile();
                        customerProfile.setUser(adminUser);

                        adminUser.setCustomerProfile(customerProfile);
                        adminUser.setAdminProfile(adminProfile);

                        adminUser.setRoles(Set.of(adminRole, customerRole));

                        // TEST USER
                        User user = new User("+976750812", "r1", passwordEncoder.encode("12345"),
                                        LocalDate.of(2024, 12, 8));

                        CustomerProfile customerProfile2 = new CustomerProfile();
                        customerProfile2.setUser(user);

                        user.setCustomerProfile(customerProfile2);
                        user.setRoles(Set.of(customerRole));

                        userRepository.saveAll(List.of(adminUser, user));
                }
        }

        // ROLES
        private void createRoles() {
                Optional<Role> existingCustomerRole = roleRepository.findByName(CUSTOMER);
                Optional<Role> existingAdminRole = roleRepository.findByName(ADMIN);
                Optional<Role> existingStaffRole = roleRepository.findByName("Staff");

                if (existingCustomerRole.isEmpty()) {
                        Set<Permission> customerPermissions = permissionRepository.findAllByNameIn(
                                        Set.of("order:create", "menu:view", "profile:manage", "order_history:view"));
                        Role customerRole = new Role(CUSTOMER, customerPermissions);
                        roleRepository.save(customerRole);
                }

                if (existingAdminRole.isEmpty()) {
                        Set<Permission> adminPermissions = new HashSet<>(permissionRepository.findAll());
                        Role adminRole = new Role(ADMIN, adminPermissions);
                        roleRepository.save(adminRole);
                }

                if (existingStaffRole.isEmpty()) {
                        Set<Permission> staffPermissions = permissionRepository.findAllByNameIn(
                                        Set.of("order:update", "menu:view"));
                        Role staffRole = new Role("Staff", staffPermissions);
                        roleRepository.save(staffRole);
                }
        }

        // PERMISSIONS
        private void createPermissions() {
                if (permissionRepository.count() > 0) {
                        return;
                }
                Set<Permission> permissions = new HashSet<>();

                // USER PERMISSIONS
                permissions.add(new Permission(null, "order:create", Collections.emptySet()));
                permissions.add(new Permission(null, "menu:view", Collections.emptySet()));
                permissions.add(new Permission(null, "profile:manage", Collections.emptySet()));
                permissions.add(new Permission(null, "order_history:view", Collections.emptySet()));

                // STAFF / COURIER PERMISSIONS
                permissions.add(new Permission(null, "order:update", Collections.emptySet()));

                // ADMIN PERMISSIONS
                permissions.add(new Permission(null, "user:manage", Collections.emptySet()));
                permissions.add(new Permission(null, "permission:set", Collections.emptySet()));

                permissionRepository.saveAll(permissions);
        }

        // ATTACHMENTS
        private Attachment createAttachment(String baseName, String type) {
                UUID id = UUID.randomUUID();
                String fileName = baseName + "." + type;

                return new Attachment(
                                id,
                                fileName,
                                fileName,
                                getContentType(type),
                                10L);
        }

        private String getContentType(String type) {
                return switch (type) {
                        case "png" -> "image/png";
                        case "jpg" -> "image/jpg";
                        case "jpeg" -> "image/jpeg";
                        case "webp" -> "image/webp";
                        case "avif" -> "image/avif";
                        default -> throw new InvalidImageTypeException(type);
                };
        }
}
