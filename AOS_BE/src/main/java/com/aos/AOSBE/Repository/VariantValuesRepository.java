package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.VariantValues;

@Repository
public interface VariantValuesRepository
		extends JpaRepository<VariantValues, Integer>, JpaSpecificationExecutor<VariantValues> {
	// Add custom query methods here if needed
}