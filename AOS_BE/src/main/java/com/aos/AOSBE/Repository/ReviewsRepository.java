package com.aos.AOSBE.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aos.AOSBE.Entity.Reviews;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Integer>, JpaSpecificationExecutor<Reviews> {
	// Add custom query methods here if needed
    List<Reviews> findByProductItemsId(Long productItemId);
    Page<Reviews> findByProductItemsId(Long productItemId, Pageable pageable);
    @Query("SELECT AVG(r.rating) FROM Reviews r WHERE r.productItems.id = ?1 AND r.createdAt BETWEEN ?2 AND ?3")
    Double findAverageRatingByProductItemIdAndCreateAtBetween(Integer productItemId, LocalDateTime startDate, LocalDateTime endDate);
    @Query("SELECT COUNT(r.rating) FROM Reviews r WHERE r.productItems.id = ?1 AND r.createdAt BETWEEN ?2 AND ?3")
    Integer countReviewsByProductItemIdAndCreateAtBetween(Integer productItemId, LocalDateTime startDate, LocalDateTime endDate);
}