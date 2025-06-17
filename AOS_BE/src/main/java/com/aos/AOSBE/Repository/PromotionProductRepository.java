package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.PromotionProduct;

@Repository
public interface PromotionProductRepository
		extends JpaRepository<PromotionProduct, Integer>, JpaSpecificationExecutor<PromotionProduct> {
	// Add custom query methods here if needed
}