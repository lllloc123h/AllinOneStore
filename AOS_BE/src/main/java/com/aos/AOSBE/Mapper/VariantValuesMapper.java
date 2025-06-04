package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class VariantValuesMapper {
	@Autowired
	private VariantsService variantsService;
	
	public VariantValuesDTOS mapper(VariantValues entity) {
		return new VariantValuesDTOS(
				    entity.getId(),
				    entity.getSignalSku(),
				    entity.getDescription(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getVariants().getId()
			);
	}
	public VariantValues mapperToObject(VariantValuesDTOS entity) {
		return new VariantValues(
					entity.getId(),
					entity.getSignalSku(),
					entity.getDescription(),
					entity.getCreatedAt(),
					entity.getUpdatedAt(),
					variantsService.variantsFindById(entity.getVariants()).orElse(null)
			);
	}
	
}
