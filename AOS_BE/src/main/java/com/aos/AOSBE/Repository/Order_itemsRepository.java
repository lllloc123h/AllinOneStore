package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Order_items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Order_itemsRepository extends JpaRepository<Order_items, Integer> {
    // Add custom query methods here if needed
}
