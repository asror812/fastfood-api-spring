package com.example.app_fast_food.bonus.entity;

import java.time.LocalDate;
import java.util.UUID;
import com.example.app_fast_food.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_bonuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBonus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bonus_id")
    private Bonus bonus;

    private LocalDate receivedDate;

    private boolean used;

    private LocalDate usedDate;

    private int remainingUses;
}
