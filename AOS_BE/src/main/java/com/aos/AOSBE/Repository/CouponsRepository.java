package com.aos.AOSBE.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Coupons;

@Repository
public interface CouponsRepository extends JpaRepository<Coupons, Integer>, JpaSpecificationExecutor<Coupons> {
	// Add custom query methods here if needed
	Optional<Coupons> findByCode(String code);
	
	@Query("SELECT c FROM Coupons c WHERE c.isActive = true AND c.endAt > CURRENT_TIMESTAMP AND c.qty > 0")
	List<Coupons> findActiveCoupons();
	
	@Query("SELECT c FROM Coupons c WHERE c.isActive = true AND c.endAt > CURRENT_TIMESTAMP AND c.qty > 0 AND c.discountType = 'FREESHIP'")
	List<Coupons> findActiveFreeshipCoupons();

	@Query("SELECT c FROM Coupons c WHERE c.isActive = true AND c.endAt > CURRENT_TIMESTAMP AND c.qty > 0 AND c.discountType = 'G-DISCOUNT'")
	List<Coupons> findActiveDiscountCoupons();

}