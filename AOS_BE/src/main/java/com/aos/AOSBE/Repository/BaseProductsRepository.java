package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.BaseProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseProductsRepository extends JpaRepository<BaseProducts, Integer> {
    // Add custom query methods here if needed
}
