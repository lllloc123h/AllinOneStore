package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Base_products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Base_productsRepository extends JpaRepository<Base_products, Integer> {
    // Add custom query methods here if needed
}
