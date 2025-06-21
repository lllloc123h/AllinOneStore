package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.ProductImagesDTOS;
import com.aos.AOSBE.Entity.ProductImages;
import com.aos.AOSBE.Service.ProductItemsService;

@Component
public class ProductImagesMapper {
	@Autowired
	private ProductItemsService productItemsService;

	public ProductImagesDTOS mapper(ProductImages entity) {
		return new ProductImagesDTOS(entity.getId(), entity.getImageUrl(), entity.getCreatedAt(), entity.getUpdatedAt(),
				entity.getProductItems().getId());
	}

	public ProductImages mapperToObject(ProductImagesDTOS entity) {
		return new ProductImages(entity.getId(), entity.getImageUrl(), entity.getCreatedAt(), entity.getUpdatedAt(),
				productItemsService.productItemsFindById(entity.getProductItems()).orElse(null));
	}

}
