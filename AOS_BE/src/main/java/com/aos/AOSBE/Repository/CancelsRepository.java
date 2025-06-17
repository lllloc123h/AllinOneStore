package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Cancels;

@Repository
public interface CancelsRepository extends JpaRepository<Cancels, Integer>, JpaSpecificationExecutor<Cancels> {
	// Add custom query methods here if needed
}