package com.aos.AOSBE.Repository;

import java.util.List;

import com.aos.AOSBE.Entity.Promotions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.PromotionProducts;

@Repository
public interface PromotionProductsRepository extends JpaRepository<PromotionProducts, Integer> {
    List<PromotionProducts> findByProductItems_Id(int productItemId);
//    @Query("SELECT pp FROM PromotionProducts pp WHERE pp.promotions.id = ?1")
//    List<PromotionProducts> findByProductItemsId(int productItemId);



    @Query("SELECT p FROM PromotionProducts p WHERE p.productItems.id = ?1 AND p.promotions.isActive = true" +
            " AND p.promotions.startAt <= CURRENT_DATE AND p.promotions.endAt >= CURRENT_DATE")
    List<PromotionProducts> findActivePromotionProductsByProductItemId(int productItemId);
}

