package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.VariantValuesDTOS;
import com.aos.AOSBE.Entity.VariantValues;
import com.aos.AOSBE.Service.VariantsService;

@Component
public class VariantValuesMapper {
	@Autowired
	private VariantsService variantsService;

	public VariantValuesDTOS mapper(VariantValues entity) {
		return new VariantValuesDTOS(entity.getId(), entity.getVariants().getName(), entity.getSignalSku(),
				entity.getDescription(), entity.getCreatedAt(), entity.getUpdatedAt(), entity.getVariants().getId());
	}

	public VariantValues mapperToObject(VariantValuesDTOS entity) {
		return new VariantValues(entity.getId(), entity.getSignalSku(), entity.getDescription(), entity.getCreatedAt(),
				entity.getUpdatedAt(), variantsService.variantsFindById(entity.getVariants()).orElse(null));
	}

}
