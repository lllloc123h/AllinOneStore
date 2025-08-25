package com.aos.AOSBE.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aos.AOSBE.Entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer>, JpaSpecificationExecutor<Orders> {

	@Query("SELECT COUNT(o) FROM Orders o WHERE o.accounts.id = :accountId AND o.discountCouponCode = :couponCode")
	long countCouponUsage(@Param("accountId") Long accountId, @Param("couponCode") String couponCode);

	@Query("SELECT o FROM Orders o WHERE o.accounts.id = ?1 AND o.shippingStatus = ?2")
	List<Orders> findAllByAccountAndKeyShippingStatus(int account, String Key);

	@Query("SELECT o FROM Orders o WHERE o.accounts.id = ?1 AND o.paymentStatus = ?2 AND o.paymentMethods.id= ?3")
	List<Orders> findAllByAccountAndKeyPaymentPending(int account, String Key, int paymentMethod);

	@Query(value = """
		    			SELECT 
		        o.order_code,
		        o.created_at,
		        a.fullname,
		        a.email,
		        a.phone,
		        CONCAT(COALESCE(ua.province, ''), ', ', COALESCE(ua.district, ''), ', ', COALESCE(ua.ward, ''), ', ', COALESCE(ua.street, '')),
		        o.payment_status,
		        o.shipping_status,
		        o.shipped_date,
		        o.final_total
		    FROM Orders o
		    JOIN accounts a on a.id = o.account_id
		    JOIN user_addresses ua on ua.account_id = a.id and ua.is_default =1
			WHERE  o.created_at BETWEEN ?1 AND ?2;
		""", nativeQuery = true)
	List<Object[]> getOrdersForExport(@Param("start") LocalDateTime startDate, @Param("end") LocalDateTime endDate);

	List<Orders> findAllByAccountsId(int accountId);

	List<Orders> findByGhnOrderCodeIsNull();

	@Query("SELECT COUNT(o) FROM Orders o WHERE o.accounts.id = :accountId AND o.freeshipCouponCode = :code")
	long countFreeshipCouponUsage(@Param("accountId") Long accountId, @Param("code") String code);

	@Query("SELECT COUNT(o) > 0 FROM Orders o " +
			"JOIN o.orderItems oi " +
			"WHERE o.accounts.id = :accountId " +
			"AND oi.productItems.id = :productItemId " +
			"AND UPPER(o.shippingStatus) = UPPER(:status)")
	boolean existsByAccountIdAndProductItemIdAndShippingStatusIgnoreCase(
			@Param("accountId") Long accountId,
			@Param("productItemId") Long productItemId,
			@Param("status") String status
	);

	// giảm giá trên tổng hóa đơn
	@Query("SELECT SUM(o.discountValue) FROM Orders o WHERE o.shippingStatus LIKE ?1 AND o.paymentStatus LIKE ?2")
	Double sumDiscountByCouponCodeByStatus(String shippingStatus, String paymentStatus);

	// chênh lệch vận phí vận chuyển phải chịu
	@Query("SElECT SUM(o.estimatedShippingFee - o.actualShippingFee) FROM Orders o WHERE o.shippingStatus LIKE ?1 AND o.paymentStatus LIKE ?2")
	Double sumShippingFeeByCouponCodeByStatus(String shippingStatus, String paymentStatus);

	@Query("SELECT o FROM Orders o WHERE o.shippingStatus = ?1 AND o.paymentStatus = ?2")
	List<Orders> findAllByShippingStatusAndPaymentStatus(String shippingStatus, String paymentStatus);

	Optional<Orders> findByGhnOrderCode(String orderCode);
}
