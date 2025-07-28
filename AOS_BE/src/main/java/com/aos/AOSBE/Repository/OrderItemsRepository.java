package com.aos.AOSBE.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.OrderItems;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer>, JpaSpecificationExecutor<OrderItems> {
	// Add custom query methods here if needed
	List<OrderItems> findByOrdersId(Integer orderId);

	@Query("SELECT sum(orderItems.qty) FROM OrderItems orderItems WHERE orderItems.productItems.id = ?1 AND orderItems.createdAt <= ?2")
	Long sumQuantityByProductId(Integer productItemId, LocalDateTime createdAt);
	@Query("SELECT sum(orderItems.qty) FROM OrderItems orderItems WHERE orderItems.productItems.id = ?1" +
			" AND orderItems.createdAt BETWEEN ?2 AND ?3")
	Long sumQuantityByProductIdAndDateRange(Integer productItemId, LocalDateTime startAt, LocalDateTime endAt);


}