package com.example.app_fast_food.bonus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bonuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "bonus", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BonusProductLink> bonusProductLinks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_id", nullable = false)
    private BonusCondition condition;

    // 1 = one-time, 0 = unlimited
    // For example:
    // birhtday bonus usageLimit := 1 and user gets bonu
    // when user changes birthdat then again tries to get this bonus we say for this
    // year bonus already used
    // holiday bonus usageLimit := 1 also works like this
    @Column(name = "usage_limit")
    private int usageLimit;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "is_active")
    private boolean isActive = true;
}
