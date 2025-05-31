package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class ShippingMethodsMapper {
	public ShippingMethodsDTOS mapper(ShippingMethods entity) {
		return new ShippingMethodsDTOS(
				    entity.id,
				    entity.orderId,
				    entity.name,
				    entity.description,
				    entity.isActive,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
