package com.example.app_fast_food.initialize;

import com.example.app_fast_food.attachment.entity.Attachment;
import com.example.app_fast_food.category.CategoryRepository;
import com.example.app_fast_food.category.entity.Category;
import com.example.app_fast_food.discount.DiscountRepository;
import com.example.app_fast_food.discount.entity.Discount;
import com.example.app_fast_food.filial.FilialRepository;
import com.example.app_fast_food.filial.entity.Filial;
import com.example.app_fast_food.filial.entity.Region;
import com.example.app_fast_food.product.ProductRepository;
import com.example.app_fast_food.product.entity.Product;
import com.example.app_fast_food.product_discounts.ProductDiscount;
import com.example.app_fast_food.product_discounts.ProductDiscountReposity;
import com.example.app_fast_food.user.UserRepository;
import com.example.app_fast_food.user.entity.Address;
import com.example.app_fast_food.user.entity.User;
import com.example.app_fast_food.user.permission.PermissionRepository;
import com.example.app_fast_food.user.permission.entity.Permission;
import com.example.app_fast_food.user.role.Role;
import com.example.app_fast_food.user.role.RoleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DatabaseInitialDataAdd implements CommandLineRunner {

        private static final String IMAGES_FOLDER_PATH = "/images/";

        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final ProductRepository productRepository;
        private final PermissionRepository permissionRepository;
        private final PasswordEncoder passwordEncoder;
        private final DiscountRepository discountRepository;
        private final CategoryRepository categoryRepository;
        private final FilialRepository filialRepository;
        private final ProductDiscountReposity productDiscountReposity;

        @Override
        public void run(String... args) {

                createPermissions();
                createRoles();
                createAdmin();

                createDiscounts();
                createCategoriesAndProducts();

                createFilials();

                // TODO:
                // - create sample checks
                // - create real attachments
                // - create i18n data
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
                // simple protection so we don't reseed again
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
                Category sides = new Category(null, "Sides", null);
                Category sauces = new Category(null, "Sauces", null);
                Category drinks = new Category(null, "Drinks", null);
                Category desserts = new Category(null, "Desserts", null);

                // -----------------------
                // SUBCATEGORIES
                // -----------------------
                // Burgers
                Category chickenBurger = new Category(null, "Chicken Burgers", burgers);
                Category beefBurger = new Category(null, "Beef Burgers", burgers);
                Category doubleBurger = new Category(null, "Double Burgers", burgers);

                // Lavash
                Category chickenLavash = new Category(null, "Chicken Lavash", lavash);
                Category beefLavash = new Category(null, "Beef Lavash", lavash);
                Category cheeseLavash = new Category(null, "Cheese Lavash", lavash);

                // Doner
                Category chickenDoner = new Category(null, "Chicken Doner", doner);
                Category beefDoner = new Category(null, "Beef Doner", doner);

                // Pizza
                Category pepperoniPizza = new Category(null, "Pepperoni Pizza", pizza);
                Category chickenPizza = new Category(null, "Chicken Pizza", pizza);

                // Sides
                Category fries = new Category(null, "Fries", sides);
                Category nuggets = new Category(null, "Nuggets", sides);

                // Drinks
                Category coldDrinks = new Category(null, "Cold Drinks", drinks);
                Category hotDrinks = new Category(null, "Hot Drinks", drinks);

                // Desserts
                Category iceCream = new Category(null, "Ice Cream", desserts);
                Category donuts = new Category(null, "Donuts", desserts);

                categoryRepository.saveAll(Arrays.asList(
                                burgers, lavash, doner, pizza, sides, sauces, drinks, desserts,
                                chickenBurger, beefBurger, doubleBurger,
                                chickenLavash, beefLavash, cheeseLavash,
                                chickenDoner, beefDoner,
                                pepperoniPizza, chickenPizza,
                                fries, nuggets,
                                coldDrinks, hotDrinks,
                                iceCream, donuts));

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
                // ATTACHMENTS
                // -----------------------
                Attachment lavashAttachment1 = createAttachment("lavash");
                Attachment lavashAttachment2 = createAttachment("lavash");

                Attachment hamburgerAttachment1 = createAttachment("hamburger");
                Attachment hamburgerAttachment2 = createAttachment("hamburger");

                Attachment colaAttachment1 = createAttachment("cocacola");
                Attachment colaAttachment2 = createAttachment("cocacola");

                Attachment pizzaAttachment1 = createAttachment("pizza");
                Attachment pizzaAttachment2 = createAttachment("pizza");

                // -----------------------
                // PRODUCTS
                // -----------------------

                Product lavashProduct = new Product(
                                null,
                                "Lavash",
                                new BigDecimal(20_000),
                                chickenLavash,
                                250,
                                lavashAttachment1,
                                lavashAttachment2);

                Product hamburgerProduct = new Product(
                                null,
                                "Hamburger",
                                new BigDecimal(25_000),
                                beefBurger,
                                300,
                                hamburgerAttachment1,
                                hamburgerAttachment2);

                Product cocaColaProduct = new Product(
                                null,
                                "Coca Cola",
                                new BigDecimal(10_000),
                                coldDrinks,
                                500,
                                colaAttachment1,
                                colaAttachment2);

                Product pizzaProduct = new Product(
                                null,
                                "Pepperoni Pizza",
                                new BigDecimal(65_000),
                                pepperoniPizza,
                                150,
                                pizzaAttachment1,
                                pizzaAttachment2);

                productRepository.saveAll(List.of(lavashProduct, hamburgerProduct, cocaColaProduct, pizzaProduct));

                // -----------------------
                // PRODUCT DISCOUNTS
                // -----------------------
                ProductDiscount pd1 = new ProductDiscount(null, cocaColaProduct, navruz);

                ProductDiscount pd2 = new ProductDiscount(null, hamburgerProduct, extra);
                ProductDiscount pd3 = new ProductDiscount(null, cocaColaProduct, extra);

                ProductDiscount pd4 = new ProductDiscount(null, hamburgerProduct, extra2);
                ProductDiscount pd5 = new ProductDiscount(null, pizzaProduct, extra2);

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

                User admin = new User(
                                null,
                                phoneNumber,
                                "asror",
                                passwordEncoder.encode("123"),
                                LocalDate.now());

                admin.setAddress(address);
                admin.setRoles(Set.of(adminRole));

                userRepository.save(admin);
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
