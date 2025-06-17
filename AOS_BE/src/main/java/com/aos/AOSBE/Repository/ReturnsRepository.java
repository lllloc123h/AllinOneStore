package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Returns;

@Repository
public interface ReturnsRepository extends JpaRepository<Returns, Integer>, JpaSpecificationExecutor<Returns> {
	// Add custom query methods here if needed
}