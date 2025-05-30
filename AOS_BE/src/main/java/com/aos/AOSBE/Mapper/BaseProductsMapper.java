package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.BaseProductsDTOS;
import com.aos.AOSBE.DTOS.CategoriesDTOS;
import com.aos.AOSBE.Entity.BaseProducts;
import com.aos.AOSBE.Entity.Categories;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class BaseProductsMapper {
	public BaseProductsDTOS mapper(BaseProducts entity) {
		return new BaseProductsDTOS(
				entity.getId(),
				entity.getName(),
				entity.getMaterial(),
				entity.getMainImage(),
				entity.isCustom(),
				entity.getTurnBuy(),
				entity.getRating(),
				entity.isDiscount(),
				entity.isActive(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.getCategories().getName());
	}
}
