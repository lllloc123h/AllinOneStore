package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.BankCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface BankCodesRepository extends JpaRepository<BankCodes, Integer>, JpaSpecificationExecutor<BankCodes> {
    // Add custom query methods here if needed
}
