package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class PromotionsMapper {
	public PromotionsDTOS mapper(Promotions entity) {
		return new PromotionsDTOS(
				    entity.id,
				    entity.name,
				    entity.description,
				    entity.type,
				    entity.discountType,
				    entity.discountValue,
				    entity.comboPrice,
				    entity.usageLimit,
				    entity.startAt,
				    entity.endAt,
				    entity.isActive,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
