package com.pfefferminzia.unfall.domain;

import jakarta.persistence.*;

@Entity
public class Coverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CoverageType type;

    private double premiumMonthly;

    private double insuredSum;

    public Coverage() {
    }

    public Coverage(Long id, CoverageType type, double premiumMonthly, double insuredSum) {
        this.id = id;
        this.type = type;
        this.premiumMonthly = premiumMonthly;
        this.insuredSum = insuredSum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CoverageType getType() {
        return type;
    }

    public void setType(CoverageType type) {
        this.type = type;
    }

    public double getPremiumMonthly() {
        return premiumMonthly;
    }

    public void setPremiumMonthly(double premiumMonthly) {
        this.premiumMonthly = premiumMonthly;
    }

    public double getInsuredSum() {
        return insuredSum;
    }

    public void setInsuredSum(double insuredSum) {
        this.insuredSum = insuredSum;
    }
}
