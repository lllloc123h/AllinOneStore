package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CustomMapper {
	@Autowired
	private ProductItemsService productItemsService;
	
	public CustomDTOS mapper(Custom entity) {
		return new CustomDTOS(
				    entity.getId(),
				    entity.getInfor(),
				    entity.getProductItems().getId()
			);
	}
	public Custom mapperToObject(CustomDTOS entity) {
		return new Custom(
					entity.getId(),
					entity.getInfor(),
					productItemsService.productItemsFindById(entity.getProductItems()).orElse(null)
			);
	}
	
}
