package com.example.app_fast_food.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "couriers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Pattern(regexp = "^(?:\\+998)?\\d{2} \\d{3}-\\d{2}-\\d{2}$")
    @Column(name = "phone_number")
    private String phoneNumber;
}
