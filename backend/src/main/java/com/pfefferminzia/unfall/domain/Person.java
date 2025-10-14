package com.pfefferminzia.unfall.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {

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
    }

    public Person(Long id, String firstName, String lastName, List<Coverage> coverages, double monthlyPremium) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.coverages = coverages;
        this.monthlyPremium = monthlyPremium;
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

    public void setCoverages(List<Coverage> coverages) {
        this.coverages = coverages;
    }

    public double getMonthlyPremium() {
        return monthlyPremium;
    }

    public void setMonthlyPremium(double monthlyPremium) {
        this.monthlyPremium = monthlyPremium;
    }
}
