package com.pfefferminzia.unfall.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {

    private static final double BASE_PERSON_PREMIUM = 5.00;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "person_id")
    private List<Coverage> coverages = new ArrayList<>();

    private double monthlyPremium;

    public Person() {
        calculateTotalMonthlyPremium();
    }

    public Person(Long id, String firstName, String lastName, List<Coverage> coverages) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.coverages = coverages;
        calculateTotalMonthlyPremium();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Coverage> getCoverages() {
        return coverages;
    }

    public double getMonthlyPremium() {
        this.calculateTotalMonthlyPremium();
        return this.monthlyPremium;
    }

    public void calculateTotalMonthlyPremium() {
        double sumOfMonthlyPremiums = this.coverages.stream().mapToDouble(Coverage::getPremiumMonthly).sum();
        this.monthlyPremium = Double.sum(BASE_PERSON_PREMIUM,sumOfMonthlyPremiums);
    }

    public void addCoverage(Coverage coverage) {
        this.coverages.add(coverage);
        this.calculateTotalMonthlyPremium();
    }
}
