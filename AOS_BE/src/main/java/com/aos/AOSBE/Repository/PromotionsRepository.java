package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Promotions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionsRepository extends JpaRepository<Promotions, Integer> {
    // Add custom query methods here if needed
}
