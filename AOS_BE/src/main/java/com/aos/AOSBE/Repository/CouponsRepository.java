package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponsRepository extends JpaRepository<Coupons, Integer> {
    // Add custom query methods here if needed
}
