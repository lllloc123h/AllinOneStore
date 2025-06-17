package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.CostHistories;

@Repository
public interface CostHistoriesRepository
		extends JpaRepository<CostHistories, Integer>, JpaSpecificationExecutor<CostHistories> {
	// Add custom query methods here if needed
}