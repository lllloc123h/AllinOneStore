package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class ProductImagesMapper {
	public ProductImagesDTOS mapper(ProductImages entity) {
		return new ProductImagesDTOS(
				    entity.getId(),
				    entity.getImage(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getProductItems().getId()
			);
	}
}
