package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.PromotionProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Promotions;

import java.util.List;

@Repository
public interface PromotionsRepository extends JpaRepository<Promotions, Integer>, JpaSpecificationExecutor<Promotions> {
	// Add custom query methods here if needed
    // kiểm tra sản phẩm trong giỏ có thuộc combo nào không
    @Query("SELECT p.promotions FROM PromotionProducts p WHERE p.productItems.id = ?1 AND p.promotions.isActive = true" +
            " AND p.promotions.startAt <= CURRENT_DATE AND p.promotions.endAt >= CURRENT_DATE")
    List<Promotions> findActivePromotionsByProductItemId(int productItemId);
}