package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
    // Add custom query methods here if needed
}
