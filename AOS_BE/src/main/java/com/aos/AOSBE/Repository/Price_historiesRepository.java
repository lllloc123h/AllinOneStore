package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Price_histories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Price_historiesRepository extends JpaRepository<Price_histories, Integer> {
    // Add custom query methods here if needed
}
