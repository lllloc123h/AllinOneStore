package com.aos.AOSBE.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.ShippingMethods;

@Repository
public interface ShippingMethodsRepository
		extends JpaRepository<ShippingMethods, Integer>, JpaSpecificationExecutor<ShippingMethods> {
	@Query("select Sm from ShippingMethods Sm where Sm.name= ?1")
	Optional<ShippingMethods> findByName(String name);
}