package com.aos.AOSBE.Service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.Coupons;
import com.aos.AOSBE.Entity.Orders;
import com.aos.AOSBE.Repository.CouponsRepository;
import com.aos.AOSBE.Repository.OrdersRepository;

@Service
public class OrdersService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private CouponsRepository couponsRepository;

	public Page<Orders> ordersFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Orders> spec = specBuilder.buildFilter(filters);
		return ordersRepository.findAll(spec, pageable);
	}

	@Transactional
	public Orders ordersSave(Orders orders) {
	    try {
	        String couponCode = orders.getDiscountCouponCode();
	        if (couponCode != null && !couponCode.trim().isEmpty()) {
	            Optional<Coupons> optionalCoupon = couponsRepository.findByCode(couponCode);
	            if (optionalCoupon.isEmpty()) {
	                throw new IllegalArgumentException("Mã giảm giá không tồn tại.");
	            }
	            Coupons coupon = optionalCoupon.get();
	            if (!coupon.isActive()) {
	                throw new IllegalArgumentException("Mã giảm giá không còn hiệu lực.");
	            }

	            long usageCount = ordersRepository.countCouponUsage((long) orders.getAccounts().getId(), couponCode);
	            if (usageCount >= coupon.getUsagePerCustomer()) {
	                throw new IllegalStateException("Bạn đã sử dụng mã này đủ số lần cho phép.");
	            }
	        }

	        return ordersRepository.save(orders);
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        throw e; 
	    }
	}



	public Optional<Orders> ordersFindById(int id) {
		return ordersRepository.findById(id);
	}

	@Transactional
	public void ordersDeleteById(int id) {
		ordersRepository.deleteById(id);
	}
}