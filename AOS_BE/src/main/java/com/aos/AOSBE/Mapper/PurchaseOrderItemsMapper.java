package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class PurchaseOrderItemsMapper {
	public PurchaseOrderItemsDTOS mapper(PurchaseOrderItems entity) {
		return new PurchaseOrderItemsDTOS(
				    entity.getId(),
				    entity.getQty(),
				    entity.getCost(),
				    entity.getTotalCost(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getProductItems().getId(),
				    entity.getPurchaseOrders().getId()
			);
	}
}
