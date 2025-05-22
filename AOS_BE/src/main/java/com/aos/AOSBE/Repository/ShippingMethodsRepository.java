package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.ShippingMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingMethodsRepository extends JpaRepository<ShippingMethods, Integer> {
    // Add custom query methods here if needed
}
