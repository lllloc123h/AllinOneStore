package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.ShippingMethods;

@Repository
public interface ShippingMethodsRepository
		extends JpaRepository<ShippingMethods, Integer>, JpaSpecificationExecutor<ShippingMethods> {
	// Add custom query methods here if needed
}