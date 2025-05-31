package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class PromotionProductMapper {
	public PromotionProductDTOS mapper(PromotionProduct entity) {
		return new PromotionProductDTOS(
				    entity.id,
				    entity.requireQty,
				    entity.isGift,
				    entity.costShare,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
