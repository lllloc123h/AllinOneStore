package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CartItemsMapper {
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private ProductItemsService productItemsService;
	
	public CartItemsDTOS mapper(CartItems entity) {
		return new CartItemsDTOS(
				    entity.getId(),
				    entity.getQty(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getAccounts().getId(),
				    entity.getProductItems().getId()
			);
	}
	public CartItems mapperToObject(CartItemsDTOS entity) {
		return new CartItems(
					entity.getId(),
					entity.getQty(),
					entity.getCreatedAt(),
					entity.getUpdatedAt(),
					accountsService.accountsFindById(entity.getAccounts()).orElse(null),
					productItemsService.productItemsFindById(entity.getProductItems()).orElse(null)
			);
	}
	
}
