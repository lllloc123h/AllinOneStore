package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.CostHistories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostHistoriesRepository extends JpaRepository<CostHistories, Integer> {
    // Add custom query methods here if needed
}
