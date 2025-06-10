package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.ProductItemsDTOS;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Service.BaseProductsService;

@Component
public class ProductItemsMapper {
	@Autowired
	private BaseProductsService baseProductsService;

	public ProductItemsDTOS mapper(ProductItems entity) {
		return new ProductItemsDTOS(entity.getId(), entity.getName(), entity.getCost(), entity.getPrice(),
				entity.isPromote(), entity.getTurnBuy(), entity.getDescription(), entity.getSku(),
				entity.getSafetyStock(), entity.getQty(), entity.getSellStart(), entity.getSellEnd(),
				entity.getCreatedAt(), entity.getUpdatedAt(), entity.getBaseProducts());
	}

	public ProductItems mapperToObject(ProductItemsDTOS entity) {
		return new ProductItems(entity.getId(), entity.getName(), entity.getCost(), entity.getPrice(),
				entity.isPromote(), entity.getTurnBuy(), entity.getDescription(), entity.getSku(),
				entity.getSafetyStock(), entity.getQty(), entity.getSellStart(), entity.getSellEnd(),
				entity.getCreatedAt(), entity.getUpdatedAt(),
				baseProductsService.baseProductsFindById(entity.getBaseProducts().getId()).orElse(null));
	}

}
