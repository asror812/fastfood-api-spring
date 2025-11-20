package com.example.app_fast_food.order;

import com.example.app_fast_food.bonus.dto.bonus.BonusResponseDTO;
import com.example.app_fast_food.check.dto.CheckCreateRequestDTO;
import com.example.app_fast_food.common.response.ApiMessageResponse;
import com.example.app_fast_food.order.dto.OrderResponseDTO;
import com.example.app_fast_food.orderItem.dto.OrderItemCreateRequestDTO;
import com.example.app_fast_food.product.dto.ProductResponseDTO;
import com.example.app_fast_food.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponseDTO>> getAllByOrderStatus(@PathVariable String status) {
        return ResponseEntity.ok(orderService.getByOrderStatus(status));
    }

    @PostMapping("/basket/items")
    public ResponseEntity<OrderResponseDTO> addItem(@AuthenticationPrincipal User user,
            @Valid @RequestBody OrderItemCreateRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.addProduct(dto, user));
    }

    @GetMapping("/basket")
    public ResponseEntity<OrderResponseDTO> getBasket(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.getBasket(user));
    }

    @PatchMapping("/basket/items/{productId}")
    public ResponseEntity<OrderResponseDTO> updateQuantity(
            @AuthenticationPrincipal User user,
            @PathVariable UUID productId,
            @RequestParam(required = true) int quantity) {
        return ResponseEntity.ok(orderService.updateQuantity(user, productId, quantity));
    }

    @DeleteMapping("/basket")
    public ResponseEntity<ApiMessageResponse> deleteBasket(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(orderService.deleteBasket(user.getId()));
    }

    @DeleteMapping("/basket/items/{productId}")
    public ResponseEntity<OrderResponseDTO> removeItem(
            @AuthenticationPrincipal User user,
            @PathVariable UUID productId) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(orderService.removeProduct(user, productId));
    }

    @GetMapping("/basket/bonus")
    public ResponseEntity<List<BonusResponseDTO>> getAvailableBonuses(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.getAvailableBonuses(user));
    }

    @PostMapping("/basket/bonus/{productId}")
    public ResponseEntity<ProductResponseDTO> chooseBonus(
            @AuthenticationPrincipal User user,
            @PathVariable UUID productId) {
        return ResponseEntity.ok(orderService.selectBonus(user, productId));
    }

    @PostMapping("/confirm")
    public ResponseEntity<ApiMessageResponse> confirmOrder(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CheckCreateRequestDTO createDTO) {
        orderService.confirmOrder(createDTO, user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
