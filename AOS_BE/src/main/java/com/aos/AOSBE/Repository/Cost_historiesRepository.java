package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Cost_histories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cost_historiesRepository extends JpaRepository<Cost_histories, Integer> {
    // Add custom query methods here if needed
}
