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
	@Query("SELECT o FROM OrderItems o WHERE o.orders.id = ?1 ")
	List<OrderItems> findByOrdersId(int orderId);

	@Query("SELECT sum(orderItems.qty) FROM OrderItems orderItems WHERE orderItems.productItems.id = ?1 AND orderItems.createdAt <= ?2")
	Long sumQuantityByProductId(Integer productItemId, LocalDateTime createdAt);
	@Query("SELECT sum(orderItems.qty) FROM OrderItems orderItems WHERE orderItems.productItems.id = ?1" +
			" AND orderItems.createdAt BETWEEN ?2 AND ?3")
	Long sumQuantityByProductIdAndDateRange(Integer productItemId, LocalDateTime startAt, LocalDateTime endAt);
	@Query("SELECT o FROM OrderItems o WHERE o.promotions.id =?1")
	List<OrderItems> findByPromotionId(int promotionId);
	@Query("SELECT o FROM OrderItems o WHERE o.productItems.id = ?1")
	List<OrderItems> findAllByProductItemId(int productItemId);
	@Query("SELECT o FROM OrderItems o WHERE  o.productItems.baseProducts.id = ?1")
	List<OrderItems> findAllByBaseId(int orderId);
	@Query("SELECT SUM(o.total) FROM OrderItems o WHERE o.productItems.id = ?1 AND o.orders.shippingStatus= 'delivered' AND o.orders.paymentStatus LIKE 'paid'")
	Double sumTotalByProductItemId(int productItemId);
	@Query("SELECT SUM(o.costAtBuy*o.qty) FROM OrderItems o WHERE o.productItems.id = ?1 AND o.orders.shippingStatus= 'delivered' AND o.orders.paymentStatus LIKE 'paid'")
	Double sumCostAtBuyByProductItemId(int productItemId);


	//ước tinh doanh thu
	// priceAtBuy * qty
	@Query("SELECT SUM(o.priceAtBuy * o.qty) FROM OrderItems o WHERE o.orders.shippingStatus LIKE ?1 AND o.orders.paymentStatus LIKE ?2")
	Double sumPriceAtBuyMultiQuantityByStatus(String shippingStatus, String paymentStatus);
// costAtBuy * qty
	@Query("SELECT SUM(o.costAtBuy * o.qty) FROM OrderItems o WHERE o.orders.shippingStatus LIKE ?1 AND o.orders.paymentStatus LIKE ?2")
	Double sumCostAtBuyMultiQuantityByStatus(String shippingStatus, String paymentStatus);
	//chênh lệch giá sản phẩm phải chịu
	@Query("SELECT SUM((o.priceAtBuy - o.sellingPrice)*o.qty) FROM OrderItems o WHERE o.orders.shippingStatus LIKE ?1 AND o.orders.paymentStatus LIKE ?2")
	Double sumDiscountProductsByStatus(String shippingStatus, String paymentStatus);


// proitem
	@Query("SELECT SUM(o.priceAtBuy * o.qty) FROM OrderItems o WHERE o.orders.shippingStatus LIKE ?1 AND o.orders.paymentStatus LIKE ?2 AND o.productItems.id = ?3")
	Double revenueProductItemByStatus(String shippingStatus, String paymentStatus, int productItemId);

	@Query("SELECT SUM(o.costAtBuy* o.qty) FROM OrderItems o WHERE o.orders.shippingStatus LIKE ?1 AND o.orders.paymentStatus LIKE ?2 AND o.productItems.id = ?3")
	Double costProductItemByStatus(String shippingStatus, String paymentStatus, int productItemId);

	@Query("SELECT SUM((o.priceAtBuy - o.sellingPrice)*o.qty) FROM OrderItems o WHERE o.orders.shippingStatus LIKE ?1 AND o.orders.paymentStatus LIKE ?2 AND o.productItems.id = ?3")
	Double discountProductItemByStatus(String shippingStatus, String paymentStatus, int productItemId);

	@Query("SELECT COUNT(o) FROM OrderItems o WHERE o.orders.shippingStatus LIKE ?1 AND o.orders.paymentStatus LIKE ?2 AND o.productItems.id = ?3")
	Integer countProductItemByStatus(String shippingStatus, String paymentStatus, int productItemId);
}