package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.CategoriesDTOS;
import com.aos.AOSBE.Entity.Categories;
import com.aos.AOSBE.Service.CatalogsService;

@Component
public class CategoriesMapper {
	@Autowired
	private CatalogsService catalogsService;

	public CategoriesDTOS mapper(Categories entity) {
		return new CategoriesDTOS(entity.getId(), entity.getName(), entity.getCreatedAt(), entity.getUpdatedAt(),
				entity.getCatalogs().getName());
	}

	public Categories mapperToObject(CategoriesDTOS entity) {
		return new Categories(entity.getId(), entity.getName(), entity.getCreatedAt(), entity.getUpdatedAt(),
				catalogsService.catalogsFindByCatalogName(entity.getCatalogs()).orElse(null));
	}

}
