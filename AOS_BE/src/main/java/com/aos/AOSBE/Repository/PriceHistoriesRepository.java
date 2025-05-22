package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.PriceHistories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceHistoriesRepository extends JpaRepository<PriceHistories, Integer> {
    // Add custom query methods here if needed
}
