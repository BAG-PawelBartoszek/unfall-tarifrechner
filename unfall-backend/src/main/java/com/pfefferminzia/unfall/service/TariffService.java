package com.pfefferminzia.unfall.service;

import com.pfefferminzia.unfall.domain.Contract;
import com.pfefferminzia.unfall.repo.ContractRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TariffService {

    private final ContractRepository repo;

    public TariffService(ContractRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Contract createAndSave(Contract contract) {
        return repo.save(calculate(contract));
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
        return repo.save(calculate(c));
    }

    private Contract calculate(Contract contract) {
        contract.calculateTotalMonthlyPremium();
        return contract;
    }
}
