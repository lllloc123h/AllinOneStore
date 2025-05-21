package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Cart_items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cart_itemsRepository extends JpaRepository<Cart_items, Integer> {
    // Add custom query methods here if needed
}
