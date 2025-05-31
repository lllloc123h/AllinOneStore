package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class ProductItemsMapper {
	public ProductItemsDTOS mapper(ProductItems entity) {
		return new ProductItemsDTOS(
				    entity.getId(),
				    entity.getName(),
				    entity.getCost(),
				    entity.getPrice(),
				    entity.isPromote(),
				    entity.getTurnBuy(),
				    entity.getDescription(),
				    entity.getSku(),
				    entity.getSafetyStock(),
				    entity.getQty(),
				    entity.getSellStart(),
				    entity.getSellEnd(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getBaseProducts().getId()
			);
	}
}
