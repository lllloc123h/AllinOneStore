package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class VariantsMapper {
	public VariantsDTOS mapper(Variants entity) {
		return new VariantsDTOS(
				    entity.id,
				    entity.name,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
