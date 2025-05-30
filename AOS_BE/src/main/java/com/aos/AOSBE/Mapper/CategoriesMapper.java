package com.aos.AOSBE.Mapper;

import com.aos.AOSBE.DTOS.CategoriesDTOS;
import com.aos.AOSBE.Entity.Categories;

public class CategoriesMapper {
	public CategoriesDTOS mapper(Categories entity) {
		return new CategoriesDTOS(entity.getId(),entity.getName(),entity.getCreatedAt(),entity.getUpdatedAt(),entity.getCatalogs().getName());
	}
}
