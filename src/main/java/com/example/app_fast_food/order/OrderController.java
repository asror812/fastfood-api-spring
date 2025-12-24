package com.example.app_fast_food.order;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.app_fast_food.bonus.dto.bonus.BonusResponseDto;
import com.example.app_fast_food.order.dto.OrderResponseDto;
import com.example.app_fast_food.orderitem.dto.OrderItemCreateRequestDTO;
import com.example.app_fast_food.product.dto.ProductResponseDto;
import com.example.app_fast_food.user.entity.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponseDto>> getAllByOrderStatus(@PathVariable("status") String status) {
        return ResponseEntity.ok(orderService.getByOrderStatus(status));
    }

    @GetMapping("/basket")
    public ResponseEntity<OrderResponseDto> getBasket(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.getBasket(user));
    }

    @PostMapping("/basket/items")
    public ResponseEntity<OrderResponseDto> addItem(
            @AuthenticationPrincipal User user,
            @Valid @org.springframework.web.bind.annotation.RequestBody OrderItemCreateRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addProduct(dto, user));
    }

    @PatchMapping("/basket/items/{productId}")
    public ResponseEntity<OrderResponseDto> updateQuantity(
            @AuthenticationPrincipal User user,
            @PathVariable("productId") UUID productId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(orderService.updateQuantity(user, productId, quantity));
    }

    @DeleteMapping("/basket/items/{productId}")
    public ResponseEntity<Void> removeItem(
            @AuthenticationPrincipal User user,
            @PathVariable UUID productId) {
        orderService.removeProduct(user, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/basket")
    public ResponseEntity<Void> deleteBasket(@AuthenticationPrincipal User user) {
        orderService.deleteBasket(user.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/basket/bonuses")
    public ResponseEntity<List<BonusResponseDto>> getAvailableBonuses(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.getAvailableBonuses(user));
    }

    @PostMapping("/basket/bonuses/{productId}")
    public ResponseEntity<ProductResponseDto> chooseBonus(
            @AuthenticationPrincipal User user,
            @PathVariable("productId") UUID productId) {
        return ResponseEntity.ok(orderService.selectBonus(user, productId));
    }

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirmOrder(@AuthenticationPrincipal User user) {
        orderService.confirmOrder(user);
        return ResponseEntity.ok().build();
    }
}
