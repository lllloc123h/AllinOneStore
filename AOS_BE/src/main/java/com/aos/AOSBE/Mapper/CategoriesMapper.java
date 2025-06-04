package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CategoriesMapper {
	@Autowired
	private CatalogsService catalogsService;
	
	public CategoriesDTOS mapper(Categories entity) {
		return new CategoriesDTOS(
				    entity.getId(),
				    entity.getName(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getCatalogs().getId()
			);
	}
	public Categories mapperToObject(CategoriesDTOS entity) {
		return new Categories(
					entity.getId(),
					entity.getName(),
					entity.getCreatedAt(),
					entity.getUpdatedAt(),
					catalogsService.catalogsFindById(entity.getCatalogs()).orElse(null)
			);
	}
	
}
