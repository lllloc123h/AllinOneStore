package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class PromotionsMapper {
	public PromotionsDTOS mapper(Promotions entity) {
		return new PromotionsDTOS(
				    entity.getId(),
				    entity.getName(),
				    entity.getDescription(),
				    entity.getType(),
				    entity.getDiscountType(),
				    entity.getDiscountValue(),
				    entity.getComboPrice(),
				    entity.getUsageLimit(),
				    entity.getStartAt(),
				    entity.getEndAt(),
				    entity.isActive(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt()
			);
	}
}
