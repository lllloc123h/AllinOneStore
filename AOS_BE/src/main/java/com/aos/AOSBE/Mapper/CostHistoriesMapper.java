package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CostHistoriesMapper {
	@Autowired
	private ProductItemsService productItemsService;
	
	public CostHistoriesDTOS mapper(CostHistories entity) {
		return new CostHistoriesDTOS(
				    entity.getId(),
				    entity.getCost(),
				    entity.getCreatedAt(),
				    entity.getProductItems().getId()
			);
	}
	public CostHistories mapperToObject(CostHistoriesDTOS entity) {
		return new CostHistories(
					entity.getId(),
					entity.getCost(),
					entity.getCreatedAt(),
					productItemsService.productItemsFindById(entity.getProductItems()).orElse(null)
			);
	}
	
}
