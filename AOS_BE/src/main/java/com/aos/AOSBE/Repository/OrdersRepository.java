package com.aos.AOSBE.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aos.AOSBE.DTOS.OrderExportDto;
import com.aos.AOSBE.Entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer>, JpaSpecificationExecutor<Orders> {

	@Query("SELECT COUNT(o) FROM Orders o WHERE o.accounts.id = :accountId AND o.discountCouponCode = :couponCode")
	long countCouponUsage(@Param("accountId") Long accountId, @Param("couponCode") String couponCode);

	@Query("SELECT o FROM Orders o WHERE o.accounts.id = ?1 AND o.shippingStatus = ?2")
	List<Orders> findAllByAccountAndKeyShippingStatus(int account, String Key);

	@Query("SELECT o FROM Orders o WHERE o.accounts.id = ?1 AND o.paymentStatus = ?2 AND o.paymentMethods.id= ?3")
	List<Orders> findAllByAccountAndKeyPaymentPending(int account, String Key, int paymentMethod);

	@Query(value ="""
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

	//doanh thu gộp chưa chiết trừ giảm giá, vận chuyển
	@Query("SELECT SUM(o.finalTotal) FROM Orders o WHERE o.shippingStatus != 'cancel'")
	Double grossRevenue();
//	doanh thu khi nhận đc hàng
	@Query("SELECT SUM(o.finalTotal) FROM Orders o WHERE o.shippingStatus = 'delivered' AND o.paymentStatus like 'Đã thanh toán'")
	Double rawNetRevenue();
	// tổng chiết trừ giả giá, vận chuyển
	@Query("SELECT SUM(o.discountValue)+(SUM(o.estimatedShippingFee)-SUM(o.actualShippingFee)) FROM Orders o WHERE o.shippingStatus = 'delivered' AND o.paymentStatus like 'Đã thanh toán'")
	Double totalCostForDiscount();
	// số đơn bán thực tế
	@Query("SELECT COUNT(o) FROM Orders o WHERE o.shippingStatus = 'delivered' AND o.paymentStatus like 'Đã thanh toán'")
	Long countDeliveredOrders();


	// sô đơn hoàn trả
	@Query("SELECT COUNT(o) FROM Orders o WHERE o.shippingStatus = 'returned'")
	Long countReturnedOrders();
	// kh nhận hàng hoặc hoàn trả cả đơn-- returned
	@Query("SELECT SUM(o.finalTotal) FROM Orders o WHERE o.shippingStatus = 'returned'")
	Double totalOrderReturned();



	// tổng đã giảm giá vận chuyển ước tính
	@Query("SELECT SUM(o.actualShippingFee) FROM Orders o WHERE o.shippingStatus != 'cancel'")
	Double totalActualShippingFee();
	// tổng giảm giá vận chuyển ước tính
	@Query("SELECT SUM(o.estimatedShippingFee) FROM Orders o WHERE o.shippingStatus != 'cancel'")
	Double totalEstimatedShippingFee();
	// giảm giá thực tế
	@Query("SELECT SUM(o.estimatedShippingFee)-SUM(o.actualShippingFee) FROM Orders o WHERE o.shippingStatus = 'delivered' AND o.paymentStatus like 'Đã thanh toán'")
	Double totalActualShippingFeeDelivered();

	// tổng chiết trừ giảm giá ước tính
	@Query("SELECT SUM(o.discountValue) FROM Orders o WHERE o.shippingStatus != 'cancel'")
	Double totalEstimatedDiscountValue();
	// Tổng chiết trừ khi đã giao
	@Query("SELECT SUM(o.discountValue) FROM Orders o WHERE o.shippingStatus = 'delivered' AND o.paymentStatus like 'Đã thanh toán'")
	Double totalDiscountValueDelivered();

	@Query("SELECT SUM(oi.costAtBuy*oi.qty) FROM OrderItems oi WHERE oi.orders.shippingStatus = 'delivered' AND oi.orders.paymentStatus like 'Đã thanh toán'")
	Double totalCostProducts();

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


}
