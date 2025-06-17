package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Custom;

@Repository
public interface CustomRepository extends JpaRepository<Custom, Integer>, JpaSpecificationExecutor<Custom> {
	// Add custom query methods here if needed
}