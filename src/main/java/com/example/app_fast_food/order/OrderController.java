package com.example.app_fast_food.order;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.app_fast_food.bonus.dto.bonus.BonusResponseDto;
import com.example.app_fast_food.order.dto.ChosenOrderDto;
import com.example.app_fast_food.order.dto.OrderConfirmRequestDto;
import com.example.app_fast_food.order.dto.OrderResponseDto;
import com.example.app_fast_food.orderitem.dto.OrderItemCreateRequestDTO;
import com.example.app_fast_food.user.dto.AuthDto;

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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDto> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> getAllByOrderStatus(@PathVariable("status") String status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    @GetMapping("/basket")
    public ResponseEntity<OrderResponseDto> getBasket(@AuthenticationPrincipal AuthDto auth) {
        return ResponseEntity.ok(orderService.getBasket(auth));
    }

    @PostMapping("/basket/items")
    public ResponseEntity<OrderResponseDto> addItem(
            @AuthenticationPrincipal AuthDto auth,
            @Valid @RequestBody OrderItemCreateRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addProduct(dto, auth));
    }

    @PatchMapping("/basket/items/{productId}")
    public ResponseEntity<OrderResponseDto> updateQuantity(
            @AuthenticationPrincipal AuthDto auth,
            @PathVariable("productId") UUID productId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(orderService.updateQuantity(auth, productId, quantity));
    }

    @DeleteMapping("/basket/items/{productId}")
    public ResponseEntity<Void> removeItem(
            @AuthenticationPrincipal AuthDto auth,
            @PathVariable("productId") UUID productId) {

        orderService.removeProduct(auth, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/basket")
    public ResponseEntity<Void> deleteBasket(@AuthenticationPrincipal AuthDto auth) {
        orderService.emptyBasket(auth);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/basket/bonuses")
    public ResponseEntity<List<BonusResponseDto>> getAvailableBonuses(@AuthenticationPrincipal AuthDto auth) {
        return ResponseEntity.ok(orderService.getAvailableBonuses(auth));
    }

    @PostMapping("/basket/bonuses")
    public ResponseEntity<OrderResponseDto> chooseBonus(
            @AuthenticationPrincipal AuthDto auth,
            @Valid @RequestBody ChosenOrderDto dto) {
        return ResponseEntity.ok(orderService.applyBonus(auth, dto));
    }

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirmOrder(@AuthenticationPrincipal AuthDto auth,
            @Valid @RequestBody OrderConfirmRequestDto dto) {
        orderService.confirmOrder(auth);
        return ResponseEntity.ok().build();
    }
}
