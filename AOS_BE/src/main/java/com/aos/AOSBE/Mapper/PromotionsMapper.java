package com.aos.AOSBE.Mapper;

import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.PromotionsDTOS;
import com.aos.AOSBE.Entity.Promotions;

@Component
public class PromotionsMapper {

	public PromotionsDTOS mapper(Promotions entity) {
		return new PromotionsDTOS(entity.getId(), entity.getName(), entity.getDescription(), entity.getType(),
				entity.getDiscountType(), entity.getDiscountValue() != null ? entity.getDiscountValue() : 0.0,
				entity.getComboPrice() != null ? entity.getComboPrice() : 0.0, entity.getUsageLimit(),
				entity.getStartAt(), entity.getEndAt(), entity.isActive(), entity.getCreatedAt(),
				entity.getUpdatedAt());
	}

	public Promotions mapperToObject(PromotionsDTOS entity) {
		return new Promotions(entity.getId(), entity.getName(), entity.getDescription(), entity.getType(),
				entity.getDiscountType(), entity.getDiscountValue(), entity.getComboPrice(), entity.getUsageLimit(),
				entity.getStartAt(), entity.getEndAt(), entity.isActive(), entity.getCreatedAt(),
				entity.getUpdatedAt());
	}

}
