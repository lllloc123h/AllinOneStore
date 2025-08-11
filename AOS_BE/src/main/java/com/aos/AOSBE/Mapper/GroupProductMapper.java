package com.aos.AOSBE.Mapper;

import com.aos.AOSBE.Entity.PromotionProducts;
import com.aos.AOSBE.Repository.ProductImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.CommonFunctions.HandleListSkuToFilter;
import com.aos.AOSBE.DTOS.BaseProductsDTOS;
import com.aos.AOSBE.DTOS.ProductItemsDTOS;
import com.aos.AOSBE.Entity.BaseProducts;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Repository.PromotionProductsRepository;
import com.aos.AOSBE.Service.CategoriesService;

@Component
public class GroupProductMapper {
	@Autowired
	private CategoriesService categoriesService;
	@Autowired
	private HandleListSkuToFilter handleListSkuToFilter;
	@Autowired
	private PromotionProductsRepository promotionProductsRepository;
	@Autowired
	private ProductImagesRepository productImagesRepository;
	public BaseProductsDTOS mapperToBaseProductsDTOS(BaseProducts entity) {
		return new BaseProductsDTOS(entity.getId(), entity.getName(), entity.getMaterial(), entity.getMainImageUrl(),
				entity.isCustom(), entity.getTurnBuy(), entity.getRating(), entity.isActive(), entity.getCreatedAt(),
				entity.getUpdatedAt(), entity.getCategories().getName());
	}

	public ProductItemsDTOS mapperToProductItemDTO(ProductItems entity, int promotionId) {
		PromotionProducts promotion = promotionProductsRepository.findAllByProductItems_IdAndPromotions_Id(entity.getId(), promotionId);
		return new ProductItemsDTOS(
				entity.getId(),
				entity.getPrice(),
				entity.getDescription(),
				handleListSkuToFilter.getDescriptionOfSku(entity.getSku()),
				entity.getQty(),
				promotion.isGift(),
				promotion.getGiftOption(),
				productImagesRepository.checkContainDefaultImagesByProductItemId(entity.getId()).get(0).getImageUrl()
		);
	}
}
