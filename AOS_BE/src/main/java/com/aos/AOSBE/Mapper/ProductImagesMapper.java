package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.ProductImagesDTOS;
import com.aos.AOSBE.Entity.ProductImages;
import com.aos.AOSBE.Repository.ProductItemsRepository;

@Component
public class ProductImagesMapper {
	@Autowired
	private ProductItemsRepository productItemsRepository;

	public ProductImagesDTOS mapper(ProductImages entity) {
		return new ProductImagesDTOS(entity.getId(), entity.getImageUrl(), entity.getCreatedAt(), entity.getUpdatedAt(),
				entity.getProductItems().getId(), entity.isDefault());
	}

	public ProductImages mapperToObject(ProductImagesDTOS entity) {
		return new ProductImages(entity.getId(), entity.getImageUrl(), entity.getCreatedAt(), entity.getUpdatedAt(),
				productItemsRepository.findById(entity.getProductItems()).orElse(null), entity.isDefault());
	}

}
