package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class OrdersMapper {
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
}
