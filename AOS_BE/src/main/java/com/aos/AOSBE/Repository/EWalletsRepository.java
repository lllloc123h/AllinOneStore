package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.EWallets;

@Repository
public interface EWalletsRepository extends JpaRepository<EWallets, Integer>, JpaSpecificationExecutor<EWallets> {
	// Add custom query methods here if needed
}