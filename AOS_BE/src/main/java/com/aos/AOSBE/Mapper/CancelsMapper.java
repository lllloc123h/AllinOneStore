package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CancelsMapper {
	@Autowired
	private OrdersService ordersService;
	
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
	public Cancels mapperToObject(CancelsDTOS entity) {
		return new Cancels(
					entity.getId(),
					entity.getReason(),
					entity.isPaid(),
					entity.getStatus(),
					entity.getCreatedAt(),
					entity.getUpdatedAt(),
					ordersService.ordersFindById(entity.getOrders()).orElse(null)
			);
	}
	
}
