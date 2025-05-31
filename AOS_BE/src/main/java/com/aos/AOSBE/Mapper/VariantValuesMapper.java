package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class VariantValuesMapper {
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
}
