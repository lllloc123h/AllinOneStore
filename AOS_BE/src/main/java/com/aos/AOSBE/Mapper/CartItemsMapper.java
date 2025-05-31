package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class CartItemsMapper {
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
}
