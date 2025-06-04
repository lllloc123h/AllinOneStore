package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderItemsMapper {
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private ProductItemsService productItemsService;
	
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
	public OrderItems mapperToObject(OrderItemsDTOS entity) {
		return new OrderItems(
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
					ordersService.ordersFindById(entity.getOrders()).orElse(null),
					productItemsService.productItemsFindById(entity.getProductItems()).orElse(null)
			);
	}
	
}
