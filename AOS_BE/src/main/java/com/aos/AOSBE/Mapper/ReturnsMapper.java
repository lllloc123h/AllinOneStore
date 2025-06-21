package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.ReturnsDTOS;
import com.aos.AOSBE.Entity.Returns;
import com.aos.AOSBE.Service.OrderItemsService;

@Component
public class ReturnsMapper {
	@Autowired
	private OrderItemsService orderItemsService;

	public ReturnsDTOS mapper(Returns entity) {
		return new ReturnsDTOS(entity.getId(), entity.getReason(), entity.getImageUrl1(), entity.getImageUrl2(),
				entity.getImageUrl3(), entity.isReturnedMoney(), entity.getIsReturnedItem(), entity.getStatus(),
				entity.getCreatedAt(), entity.getUpdatedAt(), entity.getOrderItems().getId());
	}

	public Returns mapperToObject(ReturnsDTOS entity) {
		return new Returns(entity.getId(), entity.getReason(), entity.getImage1(), entity.getImage2(),
				entity.getImage3(), entity.getIsReturnedMoney(), entity.getIsReturnedItem(), entity.getStatus(),
				entity.getCreatedAt(), entity.getUpdatedAt(),
				orderItemsService.orderItemsFindById(entity.getOrderItems()).orElse(null));
	}

}
