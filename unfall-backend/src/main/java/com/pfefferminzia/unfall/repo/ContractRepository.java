package com.pfefferminzia.unfall.repo;

import com.pfefferminzia.unfall.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
