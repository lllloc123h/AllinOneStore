package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class CancelsMapper {
	public CancelsDTOS mapper(Cancels entity) {
		return new CancelsDTOS(
				    entity.getId(),
				    entity.getReason(),
				    entity.isPaid(),
				    entity.getStatus(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getOrders().getId()
			);
	}
}
