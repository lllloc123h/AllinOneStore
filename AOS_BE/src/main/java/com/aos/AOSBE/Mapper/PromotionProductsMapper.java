package com.aos.AOSBE.Mapper;

import com.aos.AOSBE.Repository.ProductItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.PromotionProductsDTOS;
import com.aos.AOSBE.Entity.PromotionProducts;
import com.aos.AOSBE.Service.ProductItemsService;
import com.aos.AOSBE.Service.PromotionsService;

@Component
public class PromotionProductsMapper {

	@Autowired
	private ProductItemsRepository productItemsRepository;

	@Autowired
	private PromotionsService promotionsService;
	@Autowired
	private ProductItemsMapper productItemsMapper;

	public PromotionProductsDTOS mapper(PromotionProducts entity) {
		return new PromotionProductsDTOS(
				    entity.getId(),
				    entity.getRequireQty(),
				    entity.isGift(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    productItemsMapper.mapper(entity.getProductItems()),
				    entity.getPromotions().getId()
			);
	}
	public PromotionProducts mapperToObject(PromotionProductsDTOS entity) {
		return new PromotionProducts(
					entity.getId(),
					entity.getRequireQty(),
					entity.isGift(),
					entity.getCreatedAt(),
					entity.getUpdatedAt(),
					productItemsRepository.findById(entity.getProductItem().getId()).orElse(null),
					promotionsService.promotionsFindById(entity.getPromotionId()).orElse(null)
			);
	}

//  public PromotionProductsDTOS mapper(PromotionProducts entity) {
//    PromotionProductsDTOS dto = new PromotionProductsDTOS();
//    dto.setId(entity.getId());
//    dto.setRequireQty(entity.getRequireQty());
//    dto.setGift(entity.isGift());
//    dto.setCostShare(entity.getCostShare());
//    dto.setCreatedAt(entity.getCreatedAt());
//    dto.setUpdatedAt(entity.getUpdatedAt());
//
//    // Map ProductItems sang ProductItemsDTOS
//    if (entity.getProductItems() != null) {
//      ProductItems p = entity.getProductItems();
//
//      ProductItemsDTOS productDto = new ProductItemsDTOS();
//
//      dto.setProductItem(productDto);
//    }
//
//    // Map Promotion ID
//    if (entity.getPromotions() != null) {
//      dto.setPromotionId(entity.getPromotions().getId());
//    }
//
//    return dto;
//  }
}
