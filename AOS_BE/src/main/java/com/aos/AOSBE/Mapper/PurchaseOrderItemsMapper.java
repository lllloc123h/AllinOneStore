package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class PurchaseOrderItemsMapper {
	@Autowired
	private ProductItemsService productItemsService;
	@Autowired
	private PurchaseOrdersService purchaseOrdersService;
	
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
	public PurchaseOrderItems mapperToObject(PurchaseOrderItemsDTOS entity) {
		return new PurchaseOrderItems(
					entity.getId(),
					entity.getQty(),
					entity.getCost(),
					entity.getTotalCost(),
					entity.getCreatedAt(),
					entity.getUpdatedAt(),
					productItemsService.productItemsFindById(entity.getProductItems()).orElse(null),
					purchaseOrdersService.purchaseOrdersFindById(entity.getPurchaseOrders()).orElse(null)
			);
	}
	
}
