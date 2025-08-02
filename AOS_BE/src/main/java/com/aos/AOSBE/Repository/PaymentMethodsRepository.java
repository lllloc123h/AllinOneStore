package com.aos.AOSBE.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.PaymentMethods;

@Repository
public interface PaymentMethodsRepository
		extends JpaRepository<PaymentMethods, Integer>, JpaSpecificationExecutor<PaymentMethods> {
	@Query("select Pm from PaymentMethods Pm where Pm.name= ?1")
	Optional<PaymentMethods> findByName(String name);
}