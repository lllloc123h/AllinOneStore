package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Coupons;

@Repository
public interface CouponsRepository extends JpaRepository<Coupons, Integer>, JpaSpecificationExecutor<Coupons> {
	// Add custom query methods here if needed
}