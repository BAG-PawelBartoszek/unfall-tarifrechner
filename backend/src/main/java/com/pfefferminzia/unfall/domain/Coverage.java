package com.pfefferminzia.unfall.domain;

import jakarta.persistence.*;

@Entity
public class Coverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CoverageType type;

    public Coverage() {
    }

    public Coverage(CoverageType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPremiumMonthly() {
        return this.type.getPremiumMonthly();
    }

    public double getInsuredSum() {
        return this.type.getInsuredSum();
    }
}
