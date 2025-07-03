package com.aos.AOSBE.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionProductRepository
		extends JpaRepository<PromotionProduct, Integer>, JpaSpecificationExecutor<PromotionProduct> {
	// Add custom query methods here if needed
	List<PromotionProduct> findByProductItems_Id(int productItemId);
}