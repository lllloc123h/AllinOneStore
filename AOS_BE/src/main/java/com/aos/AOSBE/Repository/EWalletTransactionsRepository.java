package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.EWalletTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EWalletTransactionsRepository extends JpaRepository<EWalletTransactions, Integer> {
    // Add custom query methods here if needed
}
