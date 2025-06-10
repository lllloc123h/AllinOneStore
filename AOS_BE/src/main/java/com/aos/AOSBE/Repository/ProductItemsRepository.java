package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.ProductItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductItemsRepository extends JpaRepository<ProductItems, Integer> {
    // Add custom query methods here if needed
    @Query("SELECT MIN(p.price),MAX(p.price) FROM ProductItems p WHERE p.baseProducts.id = ?1")
    List<Double> findMinAndMaxPriceByBaseId(int id);
}
