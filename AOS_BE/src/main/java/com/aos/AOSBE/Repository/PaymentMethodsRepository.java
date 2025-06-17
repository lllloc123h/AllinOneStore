package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.PaymentMethods;

@Repository
public interface PaymentMethodsRepository
		extends JpaRepository<PaymentMethods, Integer>, JpaSpecificationExecutor<PaymentMethods> {
	// Add custom query methods here if needed
}