package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class PaymentMethodsMapper {
	public PaymentMethodsDTOS mapper(PaymentMethods entity) {
		return new PaymentMethodsDTOS(
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
