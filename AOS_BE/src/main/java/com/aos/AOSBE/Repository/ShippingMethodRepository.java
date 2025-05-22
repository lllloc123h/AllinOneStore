package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Integer> {
    // Add custom query methods here if needed
}
