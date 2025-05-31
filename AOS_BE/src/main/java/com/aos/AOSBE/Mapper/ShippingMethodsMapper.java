package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class ShippingMethodsMapper {
	public ShippingMethodsDTOS mapper(ShippingMethods entity) {
		return new ShippingMethodsDTOS(
				    entity.getId(),
				    entity.getOrderId(),
				    entity.getName(),
				    entity.getDescription(),
				    entity.isActive(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt()
			);
	}
}
