package com.aos.AOSBE.Mapper;

import java.util.List;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.PromotionProductsRepository;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.ProductItemsDTOS;
import com.aos.AOSBE.Entity.ProductImages;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Service.BaseProductsService;
import com.aos.AOSBE.Service.ProductImagesService;

@Component
public class ProductItemsMapper {
	@Autowired
	private BaseProductsService baseProductsService;

	@Autowired
	private ProductImagesService productImagesService;

	@Autowired
	private PromotionProductsRepository promotionProductsRepository;
	public ProductItemsDTOS mapper(ProductItems entity) {
		return new ProductItemsDTOS(
				    entity.getId(),
				    entity.getCost(),
				    entity.getPrice(),
				    entity.getTurnBuy(),
				    entity.getDescription(),
				    entity.getSku(),
				    entity.getSafetyStock(),
				    entity.getQty(),
				    entity.getSellStart(),
				    entity.getSellEnd(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getBaseProducts().getId(),
//					promotionProductsRepository.findAllByProductItems_Id(entity.getId()).isGift()
				null
			);
		List<ProductImages> productImages = productImagesService.findByProductItemsId(entity.getId());
		String imgURL = "";
		if (productImages.size() != 0) {
			imgURL = productImages.get(0).getImageUrl();
		}
		return new ProductItemsDTOS(entity.getId(), entity.getCost(), entity.getPrice(), entity.getTurnBuy(),
				entity.getDescription(), entity.getSku(), entity.getSafetyStock(), entity.getQty(),
				entity.getSellStart(), entity.getSellEnd(), entity.getCreatedAt(), entity.getUpdatedAt(),
				entity.getBaseProducts().getId(), entity.getBaseProducts().getName(),
				entity.getBaseProducts().getMaterial(), imgURL,
				baseProductsService.baseProductsFindById(entity.getBaseProducts().getId()).orElse(null));
	}

	public ProductItems mapperToObject(ProductItemsDTOS entity) {
		return new ProductItems(entity.getId(), entity.getCost(), entity.getPrice(), entity.getTurnBuy(),
				entity.getDescription(), entity.getSku(), entity.getSafetyStock(), entity.getQty(),
				entity.getSellStart(), entity.getSellEnd(), entity.getCreatedAt(), entity.getUpdatedAt(),
				baseProductsService.baseProductsFindById(entity.getBaseId()).orElse(null));
	}

}
