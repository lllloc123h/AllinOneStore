package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class PurchaseOrdersMapper {
	public PurchaseOrdersDTOS mapper(PurchaseOrders entity) {
		return new PurchaseOrdersDTOS(
				    entity.id,
				    entity.orderDate,
				    entity.expectedDate,
				    entity.receivedDate,
				    entity.total,
				    entity.status,
				    entity.note,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
