package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class OrdersMapper {
	public OrdersDTOS mapper(Orders entity) {
		return new OrdersDTOS(
				    entity.id,
				    entity.shippingStatus,
				    entity.estimatedShippingFee,
				    entity.freeshipCouponCode,
				    entity.actualShippingFee,
				    entity.discountCouponCode,
				    entity.discountValue,
				    entity.shippedDate,
				    entity.paymentStatus,
				    entity.note,
				    entity.point,
				    entity.finalTotal,
				    entity.orderInfor,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
