package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {
    // Add custom query methods here if needed
}
