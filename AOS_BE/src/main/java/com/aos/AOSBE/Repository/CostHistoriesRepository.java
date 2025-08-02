package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.CostHistories;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CostHistoriesRepository
		extends JpaRepository<CostHistories, Integer>, JpaSpecificationExecutor<CostHistories> {
	List<CostHistories> findCostHistoriesByProductItems_IdAndCreatedAtBetween(int productItemsId, LocalDateTime createdAtAfter, LocalDateTime createdAtBefore);
	// Add custom query methods here if needed
}