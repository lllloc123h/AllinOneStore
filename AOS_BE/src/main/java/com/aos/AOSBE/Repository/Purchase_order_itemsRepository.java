package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Purchase_order_items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Purchase_order_itemsRepository extends JpaRepository<Purchase_order_items, Integer> {
    // Add custom query methods here if needed
}
