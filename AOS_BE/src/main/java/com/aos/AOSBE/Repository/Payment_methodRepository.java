package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Payment_method;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Payment_methodRepository extends JpaRepository<Payment_method, Integer> {
    // Add custom query methods here if needed
}
