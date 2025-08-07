package com.aos.AOSBE.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.DTOS.DiscountedProductDTOS;
import com.aos.AOSBE.Entity.PromotionProducts;

@Repository
public interface PromotionProductsRepository
		extends JpaRepository<PromotionProducts, Integer>, JpaSpecificationExecutor<PromotionProducts> {
	List<PromotionProducts> findByProductItems_Id(int productItemId);
	@Query("SELECT pp FROM PromotionProducts pp WHERE pp.productItems.id = ?1 AND pp.promotions.isActive = true"
			+ " AND pp.promotions.startAt <= CURRENT_TIMESTAMP AND pp.promotions.endAt >= CURRENT_TIMESTAMP")
	List<PromotionProducts> findActivePromotionProductsByProductItemsId(int productItemId);

	@Query("SELECT pp FROM PromotionProducts pp WHERE pp.promotions.id = ?1 ")
	List<PromotionProducts> findPromotionProductsByPromotionId(int promotionId);

	PromotionProducts findAllByProductItems_IdAndPromotions_Id(int productItemsId, int promotionsId);

	List<PromotionProducts> findPromotionProductsByPromotions_Id(int promotionsId);
	@Query
	("SELECT COUNT(pp) FROM PromotionProducts pp WHERE pp.productItems.id = ?1 AND pp.promotions.startAt >= ?2 or pp.promotions.endAt <= ?3")
	int countPromotionProductsByProductItemsIdAndPromotionsStartAtAfterOrPromotionsEndAtBefore(
			int productItemsId, LocalDateTime startAt, LocalDateTime endAt);
	@Query("SELECT pp FROM PromotionProducts pp WHERE pp.productItems.id = ?1 AND pp.isGift = ?2")
	List<PromotionProducts> findPromotionProductsByProductItems_IdAndGiftIsTrue(int productItemsId, boolean gift);
	
	// PromotionProductRepository.java (dùng @Query với JPQL)
	@Query(value = """
		    SELECT 
		        pi.id AS productItemId,
		        bp.name AS productName,
		        pi.price AS originalPrice,
		        p.discount_value AS discountValue,
		        p.name AS promotionName,
		        (pi.price - p.discount_value) AS discountedPrice,
		        bp.main_image_url AS imageUrl
		    FROM promotion_products pp
		    JOIN promotions p ON pp.promotion_id = p.id
		    JOIN product_items pi ON pp.product_item_id = pi.id
		    JOIN base_products bp ON pi.base_id = bp.id
		    WHERE p.is_active = 1
		      AND p.type = 'DISCOUNT'
		      AND p.end_at > CURRENT_TIMESTAMP
		      AND pp.is_gift = 0
		""", nativeQuery = true)
		List<Map<String, Object>> findDiscountedProductsNative();
@Query("SELECT pp FROM PromotionProducts pp WHERE pp.promotions.isActive = true " +
		"AND pp.promotions.startAt <= CURRENT_TIMESTAMP AND pp.promotions.endAt >= CURRENT_TIMESTAMP " )
	List<PromotionProducts> findActivePromotionProducts();

}
