package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.PaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodsRepository extends JpaRepository<PaymentMethods, Integer> {
    // Add custom query methods here if needed
}
