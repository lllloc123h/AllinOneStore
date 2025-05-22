package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.WorkHistories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkHistoriesRepository extends JpaRepository<WorkHistories, Integer> {
    // Add custom query methods here if needed
}
