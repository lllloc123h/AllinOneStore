package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class ProductImagesMapper {
	@Autowired
	private ProductItemsService productItemsService;
	
	public ProductImagesDTOS mapper(ProductImages entity) {
		return new ProductImagesDTOS(
				    entity.getId(),
				    entity.getImage(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getProductItems().getId()
			);
	}
	public ProductImages mapperToObject(ProductImagesDTOS entity) {
		return new ProductImages(
					entity.getId(),
					entity.getImage(),
					entity.getCreatedAt(),
					entity.getUpdatedAt(),
					productItemsService.productItemsFindById(entity.getProductItems()).orElse(null)
			);
	}
	
}
