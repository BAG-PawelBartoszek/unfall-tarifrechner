package com.pfefferminzia.unfall.domain;

import org.junit.jupiter.api.Test;

import static com.pfefferminzia.unfall.domain.CoverageType.*;
import static org.assertj.core.api.Assertions.assertThat;

class ContractTest {

    @Test
    void calculateTotalMonthlyPremium_emptyContract_zeroTotalMonthlyPremium() {
        Contract contract = new Contract();
        contract.calculateTotalMonthlyPremium();

        var actual = contract.getTotalMonthlyPremium();

        assertThat(actual).isEqualTo(0.0);
    }

    @Test
    void calculateTotalMonthlyPremium_onePersonWithoutCoverages_basePersonTotalMonthlyPremium() {
        Person person = new Person();
        Contract contract = new Contract();
        contract.addPerson(person);

        var actual = contract.getTotalMonthlyPremium();

        assertThat(actual).isEqualTo(5.0);
    }

    @Test
    void calculateTotalMonthlyPremium_twoPersonsWithAllCoverages_validTotalMonthlyPremium() {
        Coverage invaliditaet = new Coverage(INVALIDITAET);
        Coverage gipsgeld = new Coverage(GIPSGELD);
        Coverage tod = new Coverage(TOD);

        Person person1 = new Person();
        Person person2 = new Person();

        Contract contract = new Contract();

        person1.addCoverage(invaliditaet);
        person1.addCoverage(gipsgeld);
        person1.addCoverage(tod);

        person2.addCoverage(invaliditaet);
        person2.addCoverage(gipsgeld);
        person2.addCoverage(tod);

        contract.addPerson(person1);
        contract.addPerson(person2);

        var actual = contract.getTotalMonthlyPremium();

        assertThat(actual).isEqualTo(49.5);
    }
}