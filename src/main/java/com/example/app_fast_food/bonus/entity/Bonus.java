package com.example.app_fast_food.bonus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bonuses")
@Getter
@Setter
@NoArgsConstructor
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "bonus", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BonusProductLink> bonusProductLinks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "condition_id", nullable = false)
    private BonusCondition condition;

    // 1 = one-time, 0 = unlimited
    @Column(name = "usage_limit")
    private int usageLimit;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "is_active")
    private boolean active = true;

    public Bonus(String name, BonusCondition condition, int usageLimit, LocalDate startDate,
            LocalDate endDate, boolean active) {
        this.name = name;
        this.condition = condition;
        this.usageLimit = usageLimit;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
    }

}
