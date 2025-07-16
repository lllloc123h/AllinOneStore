package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.aos.AOSBE.Entity.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrdersRepository extends JpaRepository<Orders, Integer>, JpaSpecificationExecutor<Orders> {

    @Query("SELECT COUNT(o) FROM Orders o WHERE o.accounts.id = :accountId AND o.discountCouponCode = :couponCode")
    long countCouponUsage(@Param("accountId") Long accountId, @Param("couponCode") String couponCode);
}
