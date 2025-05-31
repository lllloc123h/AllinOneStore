package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class ReturnsMapper {
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
}
