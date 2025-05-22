package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.ProductItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemsRepository extends JpaRepository<ProductItems, Integer> {
    // Add custom query methods here if needed
}
