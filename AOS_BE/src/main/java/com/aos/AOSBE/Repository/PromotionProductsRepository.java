package com.aos.AOSBE.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.PromotionProducts;

@Repository
public interface PromotionProductsRepository
		extends JpaRepository<PromotionProducts, Integer>, JpaSpecificationExecutor<PromotionProducts> {
	List<PromotionProducts> findByProductItems_Id(int productItemId);

	@Query("SELECT pp FROM PromotionProducts pp WHERE pp.promotions.id = ?1 ")
	List<PromotionProducts> findPromotionProductsByPromotionId(int promotionId);

	PromotionProducts findAllByProductItems_IdAndPromotions_Id(int productItemsId, int promotionsId);

	List<PromotionProducts> findPromotionProductsByPromotions_Id(int promotionsId);
	@Query
	("SELECT COUNT(pp) FROM PromotionProducts pp WHERE pp.productItems.id = ?1 AND pp.promotions.startAt >= ?2 or pp.promotions.endAt <= ?3")
	int countPromotionProductsByProductItemsIdAndPromotionsStartAtAfterOrPromotionsEndAtBefore(
			int productItemsId, LocalDateTime startAt, LocalDateTime endAt);
	List<PromotionProducts> findPromotionProductsByProductItems_IdAndGiftIsTrue(int productItemsId, boolean gift);
}
