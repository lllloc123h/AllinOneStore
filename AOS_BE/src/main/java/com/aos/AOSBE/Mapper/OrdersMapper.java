package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.OrdersDTOS;
import com.aos.AOSBE.Entity.Orders;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.PaymentMethodsService;
import com.aos.AOSBE.Service.ShippingMethodsService;

@Component
public class OrdersMapper {
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private PaymentMethodsService paymentMethodsService;
	@Autowired
	private ShippingMethodsService shippingMethodsService;

	public OrdersDTOS mapper(Orders entity) {
		return new OrdersDTOS(entity.getId(), entity.getShippingStatus(), entity.getEstimatedShippingFee(),
				entity.getFreeshipCouponCode(), entity.getActualShippingFee(), entity.getDiscountCouponCode(),
				entity.getDiscountValue(), entity.getShippedDate(), entity.getPaymentStatus(), entity.getNote(),
				entity.getPoint(), entity.getFinalTotal(), entity.getOrderInfor(), entity.getCreatedAt(),
				entity.getUpdatedAt(), entity.getAccounts().getId(), entity.getPaymentMethods().getName(),
				entity.getShippingMethods().getName(), entity.getGhnOrderCode());
	}

	public Orders mapperToObject(OrdersDTOS entity) {
		return new Orders(entity.getId(), entity.getShippingStatus(), entity.getEstimatedShippingFee(),
				entity.getFreeshipCouponCode(), entity.getActualShippingFee(), entity.getDiscountCouponCode(),
				entity.getDiscountValue(), entity.getPaymentStatus(), entity.getNote(), entity.getPoint(),
				entity.getFinalTotal(), entity.getOrderInfor(), entity.getCreatedAt(), entity.getUpdatedAt(),
				accountsService.accountsFindById(entity.getAccounts()).orElse(null),
				paymentMethodsService.paymentMethodsFindByName(entity.getPaymentMethods()).orElse(null),
				shippingMethodsService.shippingMethodsFindByName(entity.getShippingMethods()).orElse(null),
				entity.getOrderCode());
	}
}