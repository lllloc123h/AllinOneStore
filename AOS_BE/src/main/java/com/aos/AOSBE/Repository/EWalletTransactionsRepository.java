package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.EWalletTransactions;

@Repository
public interface EWalletTransactionsRepository
		extends JpaRepository<EWalletTransactions, Integer>, JpaSpecificationExecutor<EWalletTransactions> {
	// Add custom query methods here if needed
}