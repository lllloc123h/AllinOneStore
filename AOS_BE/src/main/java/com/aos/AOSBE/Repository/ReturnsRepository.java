package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Returns;

import java.time.LocalDateTime;

@Repository
public interface ReturnsRepository extends JpaRepository<Returns, Integer>, JpaSpecificationExecutor<Returns> {
	// Add custom query methods here if needed
    @Query("SELECT SUM(r.qty) FROM Returns r WHERE r.orderItems.productItems.id = ?1 and r.createdAt BETWEEN ?2 AND ?3")
    Integer findReturnsByProductItemIdAndCreateAtBetween(Integer productItemId, LocalDateTime startDate, LocalDateTime endDate);
    @Query("SELECT SUM(r.refundAmount) FROM Returns r WHERE r.refundType = 'REFUND' AND r.status = 'COMPLETED' AND r.isReturnedMoney = true ")
    Double findTotalRefundAmount();
    @Query("SELECT SUM(r.refundAmount) FROM Returns r " +
            "WHERE r.refundType = 'REFUND' AND r.status = 'COMPLETED' AND r.isReturnedMoney = true AND r.orderItems.productItems.id = ?1")
    Double findTotalRefundAmountByProductItemId(Integer productItemId);
}