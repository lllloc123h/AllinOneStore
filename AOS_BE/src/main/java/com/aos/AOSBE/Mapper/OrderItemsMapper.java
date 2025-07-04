package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class OrderItemsMapper {
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private ProductItemsService productItemsService;
	@Autowired
	private PromotionsService promotionsService;
	
	public OrderItemsDTOS mapper(OrderItems entity) {
		return new OrderItemsDTOS(
				    entity.getId(),
				    entity.getQty(),
				    entity.getCost(),
				    entity.isGift(),
				    entity.getSellingPrice(),
				    entity.getTotal(),
				    entity.getCouponCode(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getOrders().getId(),
				    entity.getProductItems().getId(),
				    entity.getPromotions().getId()
			);
	}
	public OrderItems mapperToObject(OrderItemsDTOS entity) {
		return new OrderItems(
					entity.getId(),
					entity.getQty(),
					entity.getCost(),
					entity.isGift(),
					entity.getSellingPrice(),
					entity.getTotal(),
					entity.getCouponCode(),
					entity.getCreatedAt(),
					entity.getUpdatedAt(),
					ordersService.ordersFindById(entity.getOrders()).orElse(null),
					productItemsService.productItemsFindById(entity.getProductItems()).orElse(null),
					promotionsService.promotionsFindById(entity.getPromotions()).orElse(null)
			);
	}
	
}
