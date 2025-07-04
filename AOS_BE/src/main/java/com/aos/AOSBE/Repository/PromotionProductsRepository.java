package com.aos.AOSBE.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.PromotionProducts;

@Repository
public interface PromotionProductsRepository extends JpaRepository<PromotionProducts, Integer> {
    List<PromotionProducts> findByProductItems_Id(int productItemId);
}
