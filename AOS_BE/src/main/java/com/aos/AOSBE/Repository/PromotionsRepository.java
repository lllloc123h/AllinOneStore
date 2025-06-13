package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Promotions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionsRepository extends JpaRepository<Promotions, Integer> {
   Optional<Promotions> findByNameAndTypeAndIsActive(String name, String type, boolean isActive);
    List<Promotions> findAllByTypeAndIsActive(String type, boolean isActive);
}
