package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class OrderItemsMapper {
	public OrderItemsDTOS mapper(OrderItems entity) {
		return new OrderItemsDTOS(
				    entity.id,
				    entity.promotionId,
				    entity.qty,
				    entity.cost,
				    entity.isGift,
				    entity.sellingPrice,
				    entity.total,
				    entity.couponCode,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
