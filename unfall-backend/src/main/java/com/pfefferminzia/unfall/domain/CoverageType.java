package com.pfefferminzia.unfall.domain;

public enum CoverageType {
    INVALIDITAET(200_000, 5.50),
    TOD(1_000_000, 12.00),
    GIPSGELD(1_000, 2.25);

    private final double insuredSum;
    private final double premiumMonthly;

    CoverageType(double insuredSum, double premiumMonthly) {
        this.insuredSum = insuredSum;
        this.premiumMonthly = premiumMonthly;
    }

    public double getInsuredSum() {
        return this.insuredSum;
    }

    public double getPremiumMonthly() {
        return this.premiumMonthly;
    }
}
