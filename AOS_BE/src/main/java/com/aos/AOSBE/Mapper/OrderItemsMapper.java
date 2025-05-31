package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class OrderItemsMapper {
	public OrderItemsDTOS mapper(OrderItems entity) {
		return new OrderItemsDTOS(
				    entity.getId(),
				    entity.getPromotionId(),
				    entity.getQty(),
				    entity.getCost(),
				    entity.isGift(),
				    entity.getSellingPrice(),
				    entity.getTotal(),
				    entity.getCouponCode(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getOrders().getId(),
				    entity.getProductItems().getId()
			);
	}
}
