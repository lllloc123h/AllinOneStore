package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class PromotionProductMapper {
	@Autowired
	private ProductItemsService productItemsService;
	@Autowired
	private PromotionsService promotionsService;
	
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
	public PromotionProduct mapperToObject(PromotionProductDTOS entity) {
		return new PromotionProduct(
					entity.getId(),
					entity.getRequireQty(),
					entity.isGift(),
					entity.getCostShare(),
					entity.getCreatedAt(),
					entity.getUpdatedAt(),
					productItemsService.productItemsFindById(entity.getProductItems()).orElse(null),
					promotionsService.promotionsFindById(entity.getPromotions()).orElse(null)
			);
	}
	
}
