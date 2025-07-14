package com.aos.AOSBE.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.EWalletTransactions;

@Repository
public interface EWalletTransactionsRepository
		extends JpaRepository<EWalletTransactions, Integer>, JpaSpecificationExecutor<EWalletTransactions> {
	@Query("SELECT a FROM EWalletTransactions a  WHERE a.orderId = ?1 ")
	Optional<EWalletTransactions> findByOrderId(String orderId);

	@Query("SELECT a FROM EWalletTransactions a  WHERE a.eWallets.id = ?1 ")
	List<EWalletTransactions> findByEwalletId(String ewalletId);
}