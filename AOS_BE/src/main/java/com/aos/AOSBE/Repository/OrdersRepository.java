package com.aos.AOSBE.Repository;

import java.util.List;

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

	List<Orders> findAllByAccountsId(int accountId);

}
