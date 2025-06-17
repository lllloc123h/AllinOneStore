package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Variants;

@Repository
public interface VariantsRepository extends JpaRepository<Variants, Integer>, JpaSpecificationExecutor<Variants> {
	// Add custom query methods here if needed
}