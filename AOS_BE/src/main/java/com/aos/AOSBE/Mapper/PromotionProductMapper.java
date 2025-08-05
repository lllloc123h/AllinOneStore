package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.PromotionProductDTOS;
import com.aos.AOSBE.Entity.PromotionProducts;
import com.aos.AOSBE.Service.ProductItemsService;
import com.aos.AOSBE.Service.PromotionsService;

@Component
public class PromotionProductMapper {
	@Autowired
	private ProductItemsService productItemsService;
	@Autowired
	private PromotionsService promotionsService;

//	public PromotionProductDTOS mapper(PromotionProducts entity) {
//		return new PromotionProductDTOS(entity.getId(), entity.getRequireQty(), entity.isGift(),
//				entity.getCreatedAt(), entity.getUpdatedAt(), entity.getProductItems().getId(),
//				entity.getPromotions().getId());
//	}
//
//	public PromotionProducts mapperToObject(PromotionProductDTOS entity) {
//		return new PromotionProducts(entity.getId(), entity.getRequireQty(), entity.isGift(),
//				entity.getCreatedAt(), entity.getUpdatedAt(),
//				productItemsService.productItemsFindById(entity.getProductItems()).orElse(null),
//				promotionsService.promotionsFindById(entity.getPromotions()).orElse(null));
//	}

}
