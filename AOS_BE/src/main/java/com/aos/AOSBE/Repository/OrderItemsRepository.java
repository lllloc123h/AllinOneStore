package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
    // Add custom query methods here if needed
    List<OrderItems> findByOrdersId(Integer orderId);
}
