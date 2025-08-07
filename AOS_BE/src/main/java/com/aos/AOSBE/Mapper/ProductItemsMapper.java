package com.aos.AOSBE.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.ProductImagesDTOS;
import com.aos.AOSBE.DTOS.ProductItemsDTOS;
import com.aos.AOSBE.Entity.ProductImages;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Repository.ProductImagesRepository;
import com.aos.AOSBE.Repository.PromotionsRepository;
import com.aos.AOSBE.Service.BaseProductsService;
import com.aos.AOSBE.Service.ProductImagesService;

@Component
public class ProductItemsMapper {
	@Autowired
	private BaseProductsService baseProductsService;

	@Autowired
	private ProductImagesService productImagesService;

	@Autowired
	private PromotionsRepository promotionsRepository;

	@Autowired
	private PromotionsMapper promotionsMapper;
	@Autowired
	private ProductImagesMapper productImagesMapper;

	@Autowired
	private ProductImagesRepository productImagesRepository;

	public ProductItemsDTOS mapper(ProductItems entity) {
		List<ProductImages> productImages = productImagesRepository.findByProductItemsId(entity.getId());
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
				baseProductsService.baseProductsFindById(entity.getBaseId()).orElse(null),entity.isActive());
	}

	public ProductItems mapperToObjectUpdateMethod(ProductItemsDTOS entity) {
		return new ProductItems(entity.getProductItemsId(), entity.getCost(), entity.getPrice(), entity.getTurnBuy(),
				entity.getDescription(), entity.getSku(), entity.getSafetyStock(), entity.getQty(),
				entity.getSellStart(), entity.getSellEnd(), entity.getCreatedAt(), entity.getUpdatedAt(),
				baseProductsService.baseProductsFindById(entity.getBaseId()).orElse(null), entity.isActive());
	}

	public ProductItemsDTOS mapperObjectForProductDetail(ProductItems entity) {
		List<ProductImages> productImages = productImagesRepository.findByProductItemsId(entity.getId());
		List<ProductImagesDTOS> content = new ArrayList<>();
		for (ProductImages item : productImages) {
			content.add(productImagesMapper.mapper(item));
		}
		String imgURL = "";
		if (productImages.size() != 0) {
			imgURL = productImages.get(0).getImageUrl();
		}
		return new ProductItemsDTOS(entity.getId(), entity.getCost(), entity.getPrice(), entity.getTurnBuy(),
				entity.getDescription(), entity.getSku(), entity.getSafetyStock(), entity.getQty(),
				entity.getSellStart(), entity.getSellEnd(), entity.getCreatedAt(), entity.getUpdatedAt(),
				entity.getBaseProducts().getId(), entity.getBaseProducts().getName(),
				entity.getBaseProducts().getMaterial(), imgURL,
				baseProductsService.baseProductsFindById(entity.getBaseProducts().getId()).orElse(null),
				promotionsRepository.findActivePromotionsByProductItemId(entity.getId()).stream()
						.map(promotionsMapper::mapper).collect(Collectors.toList()),
				content);
	}

}
