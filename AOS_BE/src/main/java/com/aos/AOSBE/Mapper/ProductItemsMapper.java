package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class ProductItemsMapper {
	public ProductItemsDTOS mapper(ProductItems entity) {
		return new ProductItemsDTOS(
				    entity.id,
				    entity.name,
				    entity.cost,
				    entity.price,
				    entity.isPromote,
				    entity.turnBuy,
				    entity.description,
				    entity.sku,
				    entity.safetyStock,
				    entity.qty,
				    entity.sellStart,
				    entity.sellEnd,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
