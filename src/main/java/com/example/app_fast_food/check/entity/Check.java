package com.example.app_fast_food.check.entity;

import com.example.app_fast_food.order.entity.Order;
import com.example.app_fast_food.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "checks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Check {
      @Id
      @GeneratedValue(strategy = GenerationType.UUID)
      private UUID id;

      @OneToOne
      private Order order;

      @ManyToOne
      @JoinColumn(name = "user_id")
      private User user;

      @NotBlank
      private String courier;
}
