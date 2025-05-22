package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {
    // Add custom query methods here if needed
}
