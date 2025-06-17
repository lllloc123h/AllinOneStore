package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Promotions;

@Repository
public interface PromotionsRepository extends JpaRepository<Promotions, Integer>, JpaSpecificationExecutor<Promotions> {
	// Add custom query methods here if needed
}