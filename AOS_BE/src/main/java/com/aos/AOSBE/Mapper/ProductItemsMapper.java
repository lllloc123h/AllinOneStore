package com.aos.AOSBE.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.aos.AOSBE.CommonFunctions.CommonFunctions;
import com.aos.AOSBE.CommonFunctions.HandleListSkuToFilter;
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
	@Autowired
	private HandleListSkuToFilter commonFunctions;

	public ProductItemsDTOS mapper(ProductItems entity) {
		List<ProductImages> productImages = productImagesRepository.findByProductItemsId(entity.getId());
		String imgURL = "";
		if (productImages.size() != 0) {
			imgURL = productImages.get(0).getImageUrl();
		}
		List<ProductImagesDTOS> productImagesDTOS = productImagesRepository.findByProductItemsId(entity.getId())
				.stream().map(productImagesMapper::mapper).collect(Collectors.toList());
		return new ProductItemsDTOS(entity.getId(), entity.getCost(), entity.getPrice(), entity.getTurnBuy(),
				entity.getDescription(), entity.getSku(), entity.getSafetyStock(), entity.getQty(),
				entity.getSellStart(), entity.getSellEnd(), entity.getCreatedAt(), entity.getUpdatedAt(),
				entity.getBaseProducts().getId(), entity.getBaseProducts().getName(),
				entity.getBaseProducts().getMaterial(), imgURL,
				baseProductsService.baseProductsFindById(entity.getBaseProducts().getId()).orElse(null),
				productImagesDTOS, entity.isActive());
	}
	public ProductItemsDTOS mapper2(ProductItems entity) {
		List<ProductImages> productImages = productImagesRepository.findByProductItemsId(entity.getId());
		String imgURL = "";
		if (productImages.size() != 0) {
			imgURL = productImages.get(0).getImageUrl();
		}
		List<ProductImagesDTOS> productImagesDTOS = productImagesRepository.findByProductItemsId(entity.getId())
				.stream().map(productImagesMapper::mapper).collect(Collectors.toList());
		return new ProductItemsDTOS(entity.getId(), entity.getCost(), entity.getPrice(), entity.getTurnBuy(),
				entity.getDescription(),commonFunctions.getDescriptionOfSku(entity.getSku()), entity.getSafetyStock(), entity.getQty(),
				entity.getSellStart(), entity.getSellEnd(), entity.getCreatedAt(), entity.getUpdatedAt(),
				entity.getBaseProducts().getId(), entity.getBaseProducts().getName(),
				entity.getBaseProducts().getMaterial(), imgURL,
				baseProductsService.baseProductsFindById(entity.getBaseProducts().getId()).orElse(null),
				productImagesDTOS, entity.isActive());
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
