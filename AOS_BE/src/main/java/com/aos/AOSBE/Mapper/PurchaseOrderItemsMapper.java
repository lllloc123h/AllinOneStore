package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class PurchaseOrderItemsMapper {
	public PurchaseOrderItemsDTOS mapper(PurchaseOrderItems entity) {
		return new PurchaseOrderItemsDTOS(
				    entity.id,
				    entity.qty,
				    entity.cost,
				    entity.totalCost,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
