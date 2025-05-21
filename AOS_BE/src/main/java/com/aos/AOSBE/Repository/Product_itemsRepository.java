package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Product_items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Product_itemsRepository extends JpaRepository<Product_items, Integer> {
    // Add custom query methods here if needed
}
