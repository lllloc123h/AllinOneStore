package com.aos.AOSBE.Mapper;

import com.aos.AOSBE.CommonFunctions.HandleListSkuToFilter;
import com.aos.AOSBE.Repository.VariantValuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.CartItemsDTOS;
import com.aos.AOSBE.Entity.CartItems;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.ProductItemsService;
import com.aos.AOSBE.Service.PromotionsService;

@Component
public class CartItemsMapper {
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private ProductItemsService productItemsService;
	@Autowired
	private PromotionsService promotionsService;
	@Autowired
	private HandleListSkuToFilter handleListSkuToFilter;

	public CartItemsDTOS mapper(CartItems entity) {
		return new CartItemsDTOS(
				entity.getId(),
				entity.getQty(),
				entity.getProductItems().getBaseProducts().getName(),
				entity.getProductItems().getBaseProducts().getMainImageUrl(),
				entity.getProductItems().getPrice(),
				handleListSkuToFilter.getDescriptionOfSku(entity.getProductItems().getSku()),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.getAccounts().getEmail(),
				entity.getProductItems().getId(),
				entity.getPromotions(),
				entity.getComboGroup(),
				entity.getComboQty());
	}

	public CartItems mapperToObject(CartItemsDTOS entity) {
		return new CartItems(
				entity.getId(),
				entity.getQty(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				accountsService.accountsFindByEmail(entity.getAccounts()).orElse(null),
				productItemsService.productItemsFindById(entity.getProductItems()).orElse(null),
				entity.getPromotions(),
				entity.getComboGroup(),
				entity.getComboQty());
	}

}
