package com.aos.AOSBE.Mapper;

import com.aos.AOSBE.CommonFunctions.HandleListSkuToFilter;
import com.aos.AOSBE.Entity.ProductImages;
import com.aos.AOSBE.Entity.Promotions;
import com.aos.AOSBE.Repository.ProductImagesRepository;
import com.aos.AOSBE.Repository.ProductItemsRepository;
import com.aos.AOSBE.Repository.VariantValuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.CartItemsDTOS;
import com.aos.AOSBE.Entity.CartItems;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.ProductItemsService;
import com.aos.AOSBE.Service.PromotionsService;

import java.util.List;

@Component
public class CartItemsMapper {
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private ProductItemsRepository productItemsRepository;
	@Autowired
	private PromotionsService promotionsService;
	@Autowired
	private HandleListSkuToFilter handleListSkuToFilter;
	@Autowired
	private ProductImagesRepository productImagesRepository;
	public CartItemsDTOS mapper(CartItems entity) {
		if((entity.getPromotions()) == null){
			List<Promotions> pro= promotionsService.promotionsFindByIsActiveTrueByPromotionItemId(entity.getProductItems().getId());
			if(pro.size()>0){
				entity.setPromotions(pro.get(0));
			}
		}
		return new CartItemsDTOS(
				entity.getId(),
				entity.getQty(),
				entity.getProductItems().getBaseProducts().getName(),
				productImagesRepository.checkContainDefaultImagesByProductItemId(entity.getProductItems().getId()).get(0).getImageUrl(),
				entity.getProductItems().getPrice(),
				handleListSkuToFilter.getDescriptionOfSku(entity.getProductItems().getSku()),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.getAccounts().getEmail(),
				entity.getProductItems().getId(),
				entity.getPromotions(),
				entity.getComboGroup(),
				entity.getComboQty(),
				entity.getComboGroupId(),
				entity.getIsGift(),
				productItemsRepository.findById(entity.getProductItems().getId()).get().getBaseProducts().isCustom());
	}

	public CartItems mapperToObject(CartItemsDTOS entity) {
		return new CartItems(
				entity.getId(),
				entity.getQty(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				accountsService.accountsFindByEmail(entity.getAccounts()).orElse(null),
				productItemsRepository.findById(entity.getProductItems()).orElse(null),
				entity.getPromotions(),
				entity.getComboGroup(),
				entity.getComboQty(),
				entity.getComboGroupId(),
				entity.getIsGift());
	}

}
