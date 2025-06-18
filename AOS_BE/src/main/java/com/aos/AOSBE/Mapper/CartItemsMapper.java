package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.CartItemsDTOS;
import com.aos.AOSBE.Entity.CartItems;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.ProductItemsService;

@Component
public class CartItemsMapper {
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private ProductItemsService productItemsService;

	public CartItemsDTOS mapper(CartItems entity) {
		return new CartItemsDTOS(entity.getId(), entity.getQty(), entity.getCreatedAt(), entity.getUpdatedAt(),
				entity.getAccounts().getEmail(), entity.getProductItems().getId());
	}

	public CartItems mapperToObject(CartItemsDTOS entity) {
		return new CartItems(entity.getId(), entity.getQty(), entity.getCreatedAt(), entity.getUpdatedAt(),
				accountsService.accountsFindByEmail(entity.getAccounts()).orElse(null),
				productItemsService.productItemsFindById(entity.getProductItems()).orElse(null));
	}

}
