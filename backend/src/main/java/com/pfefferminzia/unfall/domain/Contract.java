package com.pfefferminzia.unfall.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Contract {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String contractNumber;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "contract_id")
  private List<Person> persons = new ArrayList<>();

  private double totalMonthlyPremium;

    public Contract() {
    }

    public Contract(Long id, String contractNumber, List<Person> persons, double totalMonthlyPremium) {
        this.id = id;
        this.contractNumber = contractNumber;
        this.persons = persons;
        this.totalMonthlyPremium = totalMonthlyPremium;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public double getTotalMonthlyPremium() {
        return totalMonthlyPremium;
    }

    public void setTotalMonthlyPremium(double totalMonthlyPremium) {
        this.totalMonthlyPremium = totalMonthlyPremium;
    }
}
