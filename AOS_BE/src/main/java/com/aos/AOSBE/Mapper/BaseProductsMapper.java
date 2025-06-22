package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.BaseProductsDTOS;
import com.aos.AOSBE.Entity.BaseProducts;
import com.aos.AOSBE.Service.CategoriesService;

@Component
public class BaseProductsMapper {
	@Autowired
	private CategoriesService categoriesService;

	public BaseProductsDTOS mapper(BaseProducts entity) {
		return new BaseProductsDTOS(entity.getId(), entity.getName(), entity.getMaterial(), entity.getMainImageUrl(),
				entity.isCustom(), entity.getTurnBuy(), entity.getRating(), entity.isActive(), entity.getCreatedAt(),
				entity.getUpdatedAt(), entity.getCategories().getName());
	}

	public BaseProducts mapperToObject(BaseProductsDTOS entity) {
		return new BaseProducts(entity.getId(), entity.getName(), entity.getMaterial(), entity.getMainImageUrl(),
				entity.isCustom(), entity.getTurnBuy(), entity.getRating(), entity.isActive(), entity.getCreatedAt(),
				entity.getUpdatedAt(), categoriesService.categoriesFindByName(entity.getCategories()).orElse(null));
	}

}
