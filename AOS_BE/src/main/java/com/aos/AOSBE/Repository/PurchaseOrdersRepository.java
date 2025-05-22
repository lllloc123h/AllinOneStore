package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.PurchaseOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrdersRepository extends JpaRepository<PurchaseOrders, Integer> {
    // Add custom query methods here if needed
}
