package com.aos.AOSBE.Repository;

import java.util.List;

<<<<<<< Updated upstream
import org.springframework.data.jpa.repository.JpaRepository;
=======
import com.aos.AOSBE.Entity.Promotions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
>>>>>>> Stashed changes
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.PromotionProducts;

@Repository
public interface PromotionProductsRepository extends JpaRepository<PromotionProducts, Integer> {
    List<PromotionProducts> findByProductItems_Id(int productItemId);
<<<<<<< Updated upstream
}
=======

    @Query("SELECT pp FROM PromotionProducts pp WHERE pp.promotions.id = ?1 ")
    List<PromotionProducts> findPromotionProductsByPromotionId(int promotionId);
}

>>>>>>> Stashed changes
