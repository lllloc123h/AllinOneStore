package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.OrderItemsDTOS;
import com.aos.AOSBE.Entity.OrderItems;
import com.aos.AOSBE.Service.OrdersService;
import com.aos.AOSBE.Service.ProductItemsService;
import com.aos.AOSBE.Service.PromotionsService;

@Component
public class OrderItemsMapper {
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private ProductItemsService productItemsService;
	@Autowired
	private PromotionsService promotionsService;

	public OrderItemsDTOS mapper(OrderItems entity) {
		return new OrderItemsDTOS(entity.getId(), entity.getPromotions().getId(), entity.getQty(), entity.getCost(),
				entity.isGift(), entity.getSellingPrice(), entity.getTotal(), entity.getCouponCode(),
				entity.getCreatedAt(), entity.getUpdatedAt(), entity.getOrders().getId(),
				entity.getProductItems().getId());
	}

	public OrderItems mapperToObject(OrderItemsDTOS entity) {
		return new OrderItems(entity.getId(), entity.getQty(), entity.getCost(), entity.isGift(),
				entity.getSellingPrice(), entity.getTotal(), entity.getCouponCode(), entity.getCreatedAt(),
				entity.getUpdatedAt(), ordersService.ordersFindById(entity.getOrders()).orElse(null),
				productItemsService.productItemsFindById(entity.getProductItems()).orElse(null),
				promotionsService.promotionsFindById(entity.getPromotionId()).orElse(null));
	}

}
