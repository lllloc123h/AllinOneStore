package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Shipping_method;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Shipping_methodRepository extends JpaRepository<Shipping_method, Integer> {
    // Add custom query methods here if needed
}
