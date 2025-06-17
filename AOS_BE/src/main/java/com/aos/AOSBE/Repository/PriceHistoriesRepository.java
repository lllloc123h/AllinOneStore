package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.PriceHistories;

@Repository
public interface PriceHistoriesRepository
		extends JpaRepository<PriceHistories, Integer>, JpaSpecificationExecutor<PriceHistories> {
	// Add custom query methods here if needed
}