package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class CategoriesMapper {
	public CategoriesDTOS mapper(Categories entity) {
		return new CategoriesDTOS(
				    entity.getId(),
				    entity.getName(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getCatalogs().getId()
			);
	}
}
