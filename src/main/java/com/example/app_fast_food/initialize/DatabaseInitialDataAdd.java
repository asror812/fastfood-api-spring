package com.example.app_fast_food.initialize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.app_fast_food.attachment.entity.Attachment;
import com.example.app_fast_food.bonus.BonusConditionRepository;
import com.example.app_fast_food.bonus.BonusProductLinkRepository;
import com.example.app_fast_food.bonus.BonusRepository;
import com.example.app_fast_food.bonus.entity.Bonus;
import com.example.app_fast_food.bonus.entity.BonusCondition;
import com.example.app_fast_food.bonus.entity.BonusProductLink;
import com.example.app_fast_food.bonus.entity.ConditionType;
import com.example.app_fast_food.category.CategoryRepository;
import com.example.app_fast_food.category.entity.Category;
import com.example.app_fast_food.discount.DiscountRepository;
import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.filial.FilialRepository;
import com.example.app_fast_food.filial.entity.Filial;
import com.example.app_fast_food.filial.entity.Region;
import com.example.app_fast_food.order.OrderRepository;
import com.example.app_fast_food.order.entity.Order;
import com.example.app_fast_food.order.entity.OrderStatus;
import com.example.app_fast_food.order.entity.PaymentType;
import com.example.app_fast_food.orderItem.entity.OrderItem;
import com.example.app_fast_food.product.ProductRepository;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.product_discounts.ProductDiscountReposity;
import com.example.app_fast_food.product_discounts.entity.ProductDiscount;
import com.example.app_fast_food.security.JwtService;
import com.example.app_fast_food.user.UserRepository;
import com.example.app_fast_food.user.entity.Address;
import com.example.app_fast_food.user.entity.User;
import com.example.app_fast_food.user.permission.PermissionRepository;
import com.example.app_fast_food.user.permission.entity.Permission;
import com.example.app_fast_food.user.role.Role;
import com.example.app_fast_food.user.role.RoleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitialDataAdd implements CommandLineRunner {
        private static final String IMAGES_FOLDER_PATH = "/images/";

        private final PasswordEncoder passwordEncoder;

        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PermissionRepository permissionRepository;

        private final ProductRepository productRepository;
        private final DiscountRepository discountRepository;
        private final ProductDiscountReposity productDiscountReposity;
        private final CategoryRepository categoryRepository;

        private final FilialRepository filialRepository;

        private final BonusRepository bonusRepository;
        private final BonusConditionRepository bonusConditionRepository;
        private final BonusProductLinkRepository bonusProductLinkRepository;

        private final OrderRepository orderRepository;

        private final JwtService jwtService;
        private User admin;

        @Override
        public void run(String... args) {

                createPermissions();
                createRoles();
                createAdmin();

                createDiscounts();
                createCategoriesAndProducts();

                createFilials();

                createBonuses();

                createOrders();
                // TODO:
                // - create sample checks
                // - create real attachments
        }

        // ------------------------------------------------------------------------
        // BONUSES
        // ------------------------------------------------------------------------

        private void createOrders() {
                if (orderRepository.count() > 0) {
                        return;
                }

                // ------------------------------------------------------------------------
                // ORDERS
                // ------------------------------------------------------------------------

                Product cola = productRepository.findByName("Coca Cola")
                                .orElseThrow(() -> new RuntimeException("Coca Cola product not found"));
                Product lavash = productRepository.findByName("Lavash")
                                .orElseThrow(() -> new RuntimeException("Lavash producy not found"));
                Product pizza = productRepository.findByName("Pizza")
                                .orElseThrow(() -> new RuntimeException("Pizza product not found"));

                Order order = new Order(null, OrderStatus.TAKEN, PaymentType.CASH, admin);
                OrderItem orderItem1 = new OrderItem(null, 4, cola, order);
                OrderItem orderItem2 = new OrderItem(null, 1, pizza, order);
                OrderItem orderItem3 = new OrderItem(null, 10, lavash, order);

                order.getOrderItems().addAll(List.of(orderItem1, orderItem2, orderItem3));

                orderRepository.save(order);

        }

        private void createBonuses() {

                if (bonusRepository.count() > 0) {
                        return;
                }

                // --------------------------------------------------------------------
                // 1) CREATE BONUS CONDITIONS
                // --------------------------------------------------------------------

                BonusCondition holidayCondition = new BonusCondition(null, ConditionType.HOLIDAY_BONUS, "ANY");

                BonusCondition birthdayCondition = new BonusCondition(null, ConditionType.USER_BIRTHDAY, "ANY");

                BonusCondition productBonusCondition = new BonusCondition(null, ConditionType.QUANTITY, "2");

                BonusCondition firstPurchaseCondition = new BonusCondition(null, ConditionType.FIRST_PURCHASE, "1");

                BonusCondition orderTotalCondition = new BonusCondition(null, ConditionType.TOTAL_PRICE, "150000");

                bonusConditionRepository.saveAll(List.of(
                                holidayCondition,
                                birthdayCondition,
                                productBonusCondition,
                                firstPurchaseCondition,
                                orderTotalCondition));

                // --------------------------------------------------------------------
                // 2) CREATE BONUSES
                // --------------------------------------------------------------------

                Bonus birthday2025 = new Bonus(
                                null,
                                "Birthday-2025",
                                birthdayCondition,
                                1, // usage limit for birthday
                                LocalDate.now(),
                                LocalDate.now().plusMonths(12),
                                true);

                Bonus navruzBonus = new Bonus(
                                null,
                                "Navruz Bonus",
                                holidayCondition,
                                1,
                                LocalDate.now(),
                                LocalDate.now().plusWeeks(3),
                                true);

                bonusRepository.saveAll(List.of(birthday2025, navruzBonus));

                // --------------------------------------------------------------------
                // 3) LINK BONUSES TO PRODUCTS
                // --------------------------------------------------------------------

                Product lavash = productRepository.findByName("Lavash")
                                .orElseThrow();

                Product hamburger = productRepository.findByName("Hamburger")
                                .orElseThrow();

                BonusProductLink link1 = new BonusProductLink(
                                null,
                                birthday2025,
                                lavash,
                                1);

                BonusProductLink link2 = new BonusProductLink(
                                null,
                                navruzBonus,
                                hamburger,
                                2);

                bonusProductLinkRepository.saveAll(List.of(link1, link2));

                // Attach links to bonuses
                birthday2025.getBonusProductLinks().add(link1);
                navruzBonus.getBonusProductLinks().add(link2);

                bonusRepository.saveAll(List.of(birthday2025, navruzBonus));
        }

        // ------------------------------------------------------------------------
        // FILIALS
        // ------------------------------------------------------------------------

        private void createFilials() {
                if (filialRepository.count() > 0) {
                        return;
                }

                Filial filial1 = new Filial(
                                null,
                                "Babur park filial",
                                "babur park",
                                LocalTime.of(9, 0),
                                LocalTime.of(0, 0),
                                72.3422,
                                40.7828,
                                Region.ANDIJON);

                Filial filial2 = new Filial(
                                null,
                                "Registan Square filial",
                                "registan square",
                                LocalTime.of(9, 0),
                                LocalTime.of(0, 0),
                                66.9749,
                                39.6542,
                                Region.SAMARQAND);

                Filial filial3 = new Filial(
                                null,
                                "Chorsu Bazaar filial",
                                "chorsu bazaar",
                                LocalTime.of(9, 0),
                                LocalTime.of(0, 0),
                                69.2435,
                                41.3270,
                                Region.TASHKENT);

                Filial filial4 = new Filial(
                                null,
                                "Tashkent Tower filial",
                                "tashkent tower",
                                LocalTime.of(9, 0),
                                LocalTime.of(0, 0),
                                69.3341,
                                41.3385,
                                Region.TASHKENT);

                filialRepository.saveAll(Arrays.asList(filial1, filial2, filial3, filial4));
        }

        // ------------------------------------------------------------------------
        // CATEGORIES + PRODUCTS + PRODUCT DISCOUNTS
        // ------------------------------------------------------------------------

        @Transactional
        public void createCategoriesAndProducts() {
                if (categoryRepository.count() > 0 || productRepository.count() > 0) {
                        return;
                }

                // -----------------------
                // PARENT CATEGORIES
                // -----------------------
                Category burgers = new Category(null, "Burgers & Sandwiches", null);
                Category lavash = new Category(null, "Rolls & Lavash", null);
                Category doner = new Category(null, "Doners / Shawarma", null);
                Category pizza = new Category(null, "Pizza", null);
                Category drinks = new Category(null, "Drinks", null);
                Category desserts = new Category(null, "Desserts", null);

                // -----------------------
                // SUBCATEGORIES
                // -----------------------
                // Burgers
                Category beefBurger = new Category(null, "Beef Burgers", burgers);

                // Lavash
                Category beefLavash = new Category(null, "Beef Lavash", lavash);
                Category cheeseLavash = new Category(null, "Cheese Lavash", lavash);

                // Doner
                Category beefDoner = new Category(null, "Beef Doner", doner);

                // Pizza
                Category pepperoniPizza = new Category(null, "Pepperoni Pizza", pizza);

                // Drinks
                Category coldDrinks = new Category(null, "Cold Drinks", drinks);

                // Desserts
                Category iceCream = new Category(null, "Ice Cream", desserts);
                Category donuts = new Category(null, "Donuts", desserts);

                categoryRepository.saveAll(Arrays.asList(
                                burgers, lavash, doner, pizza, drinks, desserts,
                                beefBurger, beefLavash, cheeseLavash, beefDoner,
                                pepperoniPizza, coldDrinks, iceCream, donuts));

                // -----------------------
                // DISCOUNTS
                // -----------------------
                Discount extra = discountRepository.findByNameWithProducts("Extra")
                                .orElseThrow(() -> new RuntimeException("Discount 'Extra' not found"));
                Discount extra2 = discountRepository.findByNameWithProducts("Extra-2")
                                .orElseThrow(() -> new RuntimeException("Discount 'Extra-2' not found"));
                Discount navruz = discountRepository.findByNameWithProducts("Navruz")
                                .orElseThrow(() -> new RuntimeException("Discount 'Navruz' not found"));

                // -----------------------
                // PRODUCTS
                // -----------------------

                // Beef Burger
                Product classicBeefBurger = new Product(
                                null,
                                "Classic Beef Burger",
                                new BigDecimal(23000),
                                beefBurger,
                                280,
                                createAttachment("classic_beef_burger"),
                                createAttachment("classic_beef_burger"));

                Product cheeseBurger = new Product(
                                null,
                                "Cheeseburger",
                                new BigDecimal(30000),
                                beefBurger,
                                380,
                                createAttachment("cheese_burger"),
                                createAttachment("cheese_burger"));

                // Beef Lavash
                Product classicBeefLavash = new Product(
                                null,
                                "Classic Beef Lavash",
                                new BigDecimal(23000),
                                beefLavash,
                                280,
                                createAttachment("classic_beef_lavash"),
                                createAttachment("classic_beef_lavash"));

                Product beefLavashWithCheese = new Product(
                                null,
                                "Beef Lavash with Cheese",
                                new BigDecimal(30000),
                                beefLavash,
                                380,
                                createAttachment("beef_lavash_with_cheese"),
                                createAttachment("beefe_lavash_with_cheese"));

                // Pepperoni Pizza
                Product pepperoniMedium = new Product(
                                null,
                                "Pepperoni Medium",
                                new BigDecimal(23000),
                                beefBurger,
                                280,
                                createAttachment("pepperoni_medium"),
                                createAttachment("pepperoni_medium"));

                Product pepperoniLarge = new Product(
                                null,
                                "Pepperoni Large",
                                new BigDecimal(30000),
                                beefBurger,
                                380,
                                createAttachment("pepperoni_large"),
                                createAttachment("pepperoni_large"));

                // Ice Cream
                Product vanillaIceCream = new Product(
                                null,
                                "Vanilla Ice Cream Cup",
                                new BigDecimal(20_000),
                                iceCream,
                                250,
                                createAttachment("vanilla_ice_cream"),
                                createAttachment("vanilla_ice_cream"));

                Product chocolateIceCream = new Product(
                                null,
                                "Chocolate Ice Cream Cup",
                                new BigDecimal(20_000),
                                iceCream,
                                250,
                                createAttachment("chocolate_ice_cream"),
                                createAttachment("chocolate_ice_cream"));
                // Cold drinks
                Product sprite = new Product(
                                null,
                                "Sprite 0.5L",
                                new BigDecimal(25_000),
                                coldDrinks,
                                300,
                                createAttachment("sprite"),
                                createAttachment("sprite"));

                Product cocaCola = new Product(
                                null,
                                "Coca Cola",
                                new BigDecimal(10_000),
                                coldDrinks,
                                500,
                                createAttachment("cocacola"),
                                createAttachment("cocacola"));

                productRepository.saveAll(List.of(classicBeefBurger, cheeseBurger, vanillaIceCream, chocolateIceCream,
                                cocaCola, sprite, pepperoniLarge, pepperoniMedium, classicBeefLavash,
                                beefLavashWithCheese));

                // -----------------------
                // PRODUCT DISCOUNTS
                // -----------------------
                ProductDiscount pd1 = new ProductDiscount(null, cocaCola, navruz);

                ProductDiscount pd2 = new ProductDiscount(null, pepperoniLarge, extra);
                ProductDiscount pd3 = new ProductDiscount(null, pepperoniLarge, extra);

                ProductDiscount pd4 = new ProductDiscount(null, vanillaIceCream, extra2);
                ProductDiscount pd5 = new ProductDiscount(null, chocolateIceCream, extra2);

                productDiscountReposity.saveAll(List.of(pd1, pd2, pd3, pd4, pd5));
        }

        // ------------------------------------------------------------------------
        // DISCOUNTS
        // ------------------------------------------------------------------------

        private void createDiscounts() {
                if (discountRepository.count() > 0) {
                        return;
                }

                Discount discount1 = new Discount(
                                null,
                                "Navruz",
                                10,
                                LocalDate.now(),
                                LocalDate.now().plusDays(10),
                                0,
                                true);

                Discount discount2 = new Discount(
                                null,
                                "Extra",
                                15,
                                LocalDate.now(),
                                LocalDate.now().plusDays(10),
                                3,
                                true);

                Discount discount3 = new Discount(
                                null,
                                "Extra-2",
                                5,
                                LocalDate.now(),
                                LocalDate.now().plusDays(5),
                                2,
                                true);

                discountRepository.saveAll(List.of(discount1, discount2, discount3));
        }

        // ------------------------------------------------------------------------
        // ADMIN USER
        // ------------------------------------------------------------------------

        private void createAdmin() {
                Role adminRole = roleRepository.findByName("Admin")
                                .orElseThrow(() -> new RuntimeException("No admin role found"));

                String phoneNumber = "+97 675-00-00";

                if (userRepository.existsByPhoneNumber(phoneNumber)) {
                        return;
                }

                Address address = new Address(null, 69.24007340, 41.29949580);

                admin = new User(
                                null,
                                phoneNumber,
                                "asror",
                                passwordEncoder.encode("123"),
                                LocalDate.now());

                admin.setAddress(address);
                admin.setRoles(Set.of(adminRole));

                userRepository.save(admin);

                String token = jwtService.generateToken(phoneNumber);

                log.warn("Token received {}", token);
        }

        // ------------------------------------------------------------------------
        // ROLES
        // ------------------------------------------------------------------------

        private void createRoles() {
                Optional<Role> existingUserRole = roleRepository.findByName("User");
                Optional<Role> existingAdminRole = roleRepository.findByName("Admin");
                Optional<Role> existingStaffRole = roleRepository.findByName("Staff");

                if (existingUserRole.isEmpty()) {
                        Set<Permission> userPermissions = permissionRepository.findAllByNameIn(
                                        Set.of("order:create", "menu:view", "profile:manage", "order_history:view"));
                        Role userRole = new Role("User", userPermissions, Collections.emptySet());
                        roleRepository.save(userRole);
                }

                if (existingAdminRole.isEmpty()) {
                        Set<Permission> adminPermissions = new HashSet<>(permissionRepository.findAll());
                        Role adminRole = new Role("Admin", adminPermissions, Collections.emptySet());
                        roleRepository.save(adminRole);
                }

                if (existingStaffRole.isEmpty()) {
                        Set<Permission> staffPermissions = permissionRepository.findAllByNameIn(
                                        Set.of("order:update", "menu:view"));
                        Role staffRole = new Role("Staff", staffPermissions, Collections.emptySet());
                        roleRepository.save(staffRole);
                }
        }

        // ------------------------------------------------------------------------
        // PERMISSIONS
        // ------------------------------------------------------------------------

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

        // ------------------------------------------------------------------------
        // ATTACHMENTS
        // ------------------------------------------------------------------------

        private Attachment createAttachment(String originalBaseName) {
                String storedName = UUID.randomUUID() + ".jpg";

                return new Attachment(
                                null,
                                originalBaseName + ".jpg",
                                storedName,
                                "image/jpeg",
                                10L,
                                IMAGES_FOLDER_PATH + storedName);
        }
}
