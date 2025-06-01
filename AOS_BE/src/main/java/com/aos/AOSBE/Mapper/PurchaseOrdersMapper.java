package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class PurchaseOrdersMapper {
	
	public PurchaseOrdersDTOS mapper(PurchaseOrders entity) {
		return new PurchaseOrdersDTOS(
				    entity.getId(),
				    entity.getOrderDate(),
				    entity.getExpectedDate(),
				    entity.getReceivedDate(),
				    entity.getTotal(),
				    entity.getStatus(),
				    entity.getNote(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt()
			);
	}
	public PurchaseOrders mapperToObject(PurchaseOrdersDTOS entity) {
		return new PurchaseOrders(
					entity.getId(),
					entity.getOrderDate(),
					entity.getExpectedDate(),
					entity.getReceivedDate(),
					entity.getTotal(),
					entity.getStatus(),
					entity.getNote(),
					entity.getCreatedAt(),
					entity.getUpdatedAt()
			);
	}
	
}
