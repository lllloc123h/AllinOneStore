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
            " AND p.promotions.startAt <= CURRENT_TIMESTAMP AND p.promotions.endAt >= CURRENT_TIMESTAMP")
    List<Promotions> findActivePromotionsByProductItemId(int productItemId);


    @Query(value = "SELECT TOP 1 p.type,p.discount_type,p.discount_value " +
            "FROM promotion_products pp JOIN promotions p " +
            "ON pp.promotion_id = p.id WHERE pp.product_item_id = 2 " +
            "AND p.is_active = 1 A" +
            "ND p.start_at <= CURRENT_TIMESTAMP " +
            "AND p.end_at >= CURRENT_TIMESTAMP ", nativeQuery = true)
    Object[] findFirstTypePromotionByProductItemId(int productItemId);
}