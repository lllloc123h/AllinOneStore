package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class VariantValuesMapper {
	public VariantValuesDTOS mapper(VariantValues entity) {
		return new VariantValuesDTOS(
				    entity.id,
				    entity.signalSku,
				    entity.description,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
