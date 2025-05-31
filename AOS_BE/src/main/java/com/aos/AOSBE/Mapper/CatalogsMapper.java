package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class CatalogsMapper {
	public CatalogsDTOS mapper(Catalogs entity) {
		return new CatalogsDTOS(
				    entity.id,
				    entity.name,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
