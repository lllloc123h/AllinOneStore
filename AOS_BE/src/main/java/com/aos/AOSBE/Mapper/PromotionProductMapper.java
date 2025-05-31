package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class PromotionProductMapper {
	public PromotionProductDTOS mapper(PromotionProduct entity) {
		return new PromotionProductDTOS(
				    entity.getId(),
				    entity.getRequireQty(),
				    entity.isGift(),
				    entity.getCostShare(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getProductItems().getId(),
				    entity.getPromotions().getId()
			);
	}
}
