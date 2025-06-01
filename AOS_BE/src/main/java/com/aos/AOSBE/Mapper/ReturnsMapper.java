package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class ReturnsMapper {
	@Autowired
	private OrderItemsService orderItemsService;
	
	public ReturnsDTOS mapper(Returns entity) {
		return new ReturnsDTOS(
				    entity.getId(),
				    entity.getReason(),
				    entity.getImage1(),
				    entity.getImage2(),
				    entity.getImage3(),
				    entity.getIsReturnedMoney(),
				    entity.getIsReturnedItem(),
				    entity.getStatus(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getOrderItems().getId()
			);
	}
	public Returns mapperToObject(ReturnsDTOS entity) {
		return new Returns(
					entity.getId(),
					entity.getReason(),
					entity.getImage1(),
					entity.getImage2(),
					entity.getImage3(),
					entity.getIsReturnedMoney(),
					entity.getIsReturnedItem(),
					entity.getStatus(),
					entity.getCreatedAt(),
					entity.getUpdatedAt(),
					orderItemsService.orderItemsFindById(entity.getOrderItems()).orElse(null)
			);
	}
	
}
