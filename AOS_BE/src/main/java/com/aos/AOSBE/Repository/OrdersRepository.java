package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    // Add custom query methods here if needed
}
