package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class PriceHistoriesMapper {
	@Autowired
	private ProductItemsService productItemsService;
	
	public PriceHistoriesDTOS mapper(PriceHistories entity) {
		return new PriceHistoriesDTOS(
				    entity.getId(),
				    entity.getPrice(),
				    entity.getCreatedAt(),
				    entity.getProductItems().getId()
			);
	}
	public PriceHistories mapperToObject(PriceHistoriesDTOS entity) {
		return new PriceHistories(
					entity.getId(),
					entity.getPrice(),
					entity.getCreatedAt(),
					productItemsService.productItemsFindById(entity.getProductItems()).orElse(null)
			);
	}
	
}
