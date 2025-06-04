package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class ProductItemsMapper {
	@Autowired
	private BaseProductsService baseProductsService;
	
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
	public ProductItems mapperToObject(ProductItemsDTOS entity) {
		return new ProductItems(
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
					baseProductsService.baseProductsFindById(entity.getBaseProducts()).orElse(null)
			);
	}
	
}
