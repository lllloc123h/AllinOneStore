package com.aos.AOSBE.Repository;

import java.util.List;

import com.aos.AOSBE.Entity.Promotions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.PromotionProducts;

@Repository
public interface PromotionProductsRepository extends JpaRepository<PromotionProducts, Integer>, JpaSpecificationExecutor<PromotionProducts> {
    List<PromotionProducts> findByProductItems_Id(int productItemId);

    @Query("SELECT pp FROM PromotionProducts pp WHERE pp.promotions.id = ?1 ")
    List<PromotionProducts> findPromotionProductsByPromotionId(int promotionId);

    PromotionProducts findAllByProductItems_IdAndPromotions_Id(int productItemsId, int promotionsId);
}

