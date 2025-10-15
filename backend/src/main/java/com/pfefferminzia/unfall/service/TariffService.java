package com.pfefferminzia.unfall.service;

import com.pfefferminzia.unfall.domain.Contract;
import com.pfefferminzia.unfall.domain.Coverage;
import com.pfefferminzia.unfall.domain.Person;
import com.pfefferminzia.unfall.repo.ContractRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TariffService {

    private static final double BASE_PERSON_PREMIUM = 5.00;

    private final ContractRepository repo;

    public TariffService(ContractRepository repo) {
        this.repo = repo;
    }

    public Contract calculate(Contract contract) {
        double contractSum = 0.0;
        contract.setTotalMonthlyPremium(round2(contractSum));
        return contract;
    }

    @Transactional
    public Contract createAndSave(Contract contract) {
        calculate(contract);
        return repo.save(contract);
    }

    @Transactional(readOnly = true)
    public Contract getById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<Contract> listAll() {
        return repo.findAll();
    }

    @Transactional
    public Contract recalculateAndSave(Long id) {
        Contract c = getById(id);
        calculate(c);
        return repo.save(c);
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
