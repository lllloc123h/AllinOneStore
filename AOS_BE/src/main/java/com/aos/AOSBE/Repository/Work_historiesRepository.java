package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Work_histories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Work_historiesRepository extends JpaRepository<Work_histories, Integer> {
    // Add custom query methods here if needed
}
