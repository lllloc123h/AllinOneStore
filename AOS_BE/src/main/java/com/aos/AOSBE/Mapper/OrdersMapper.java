package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class OrdersMapper {
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private PaymentMethodsService paymentMethodsService;
	@Autowired
	private ShippingMethodsService shippingMethodsService;
	
	public OrdersDTOS mapper(Orders entity) {
		return new OrdersDTOS(
				    entity.getId(),
				    entity.getShippingStatus(),
				    entity.getEstimatedShippingFee(),
				    entity.getFreeshipCouponCode(),
				    entity.getActualShippingFee(),
				    entity.getDiscountCouponCode(),
				    entity.getDiscountValue(),
				    entity.getShippedDate(),
				    entity.getPaymentStatus(),
				    entity.getNote(),
				    entity.getPoint(),
				    entity.getFinalTotal(),
				    entity.getOrderInfor(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getAccounts().getId(),
				    entity.getPaymentMethods().getId(),
				    entity.getShippingMethods().getId()
			);
	}
	public Orders mapperToObject(OrdersDTOS entity) {
		return new Orders(
					entity.getId(),
					entity.getShippingStatus(),
					entity.getEstimatedShippingFee(),
					entity.getFreeshipCouponCode(),
					entity.getActualShippingFee(),
					entity.getDiscountCouponCode(),
					entity.getDiscountValue(),
					entity.getShippedDate(),
					entity.getPaymentStatus(),
					entity.getNote(),
					entity.getPoint(),
					entity.getFinalTotal(),
					entity.getOrderInfor(),
					entity.getCreatedAt(),
					entity.getUpdatedAt(),
					accountsService.accountsFindById(entity.getAccounts()).orElse(null),
					paymentMethodsService.paymentMethodsFindById(entity.getPaymentMethods()).orElse(null),
					shippingMethodsService.shippingMethodsFindById(entity.getShippingMethods()).orElse(null)
			);
	}
	
}
