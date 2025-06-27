package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class ReturnsMapper {
	@Autowired
	private OrderItemsService orderItemsService;
	
	public ReturnsDTOS mapper(Returns entity) {
		return new ReturnsDTOS(
				    entity.getId(),
				    entity.getQty(),
				    entity.getReason(),
				    entity.getImageUrl1(),
				    entity.getImageUrl2(),
				    entity.getImageUrl3(),
				    entity.getVideoUrl(),
				    entity.isReturnedMoney(),
				    entity.getRefundAmount(),
				    entity.getReturnType(),
				    entity.getRefundType(),
				    entity.getStatus(),
				    entity.getProcessedAt(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getOrderItems().getId()
			);
	}
	public Returns mapperToObject(ReturnsDTOS entity) {
		return new Returns(
					entity.getId(),
					entity.getQty(),
					entity.getReason(),
					entity.getImageUrl1(),
					entity.getImageUrl2(),
					entity.getImageUrl3(),
					entity.getVideoUrl(),
					entity.isReturnedMoney(),
					entity.getRefundAmount(),
					entity.getReturnType(),
					entity.getRefundType(),
					entity.getStatus(),
					entity.getProcessedAt(),
					entity.getCreatedAt(),
					entity.getUpdatedAt(),
					orderItemsService.orderItemsFindById(entity.getOrderItems()).orElse(null)
			);
	}
	
}
