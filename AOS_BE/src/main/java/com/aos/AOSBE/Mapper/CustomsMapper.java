package com.aos.AOSBE.Mapper;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CustomsMapper {
	@Autowired
	private ProductItemsService productItemsService;
	@Autowired
	private OrderItemsMapper orderItemsMapper;
	@Autowired
	private ProductItemsMapper productItemsMapper;
	public CustomsDTOS mapper(Customs entity) {
		return new CustomsDTOS(
				    entity.getId(),
				    entity.getAccount().getId(),
				    entity.getCanvasJson(),
				    entity.getImageUrl(),
				    entity.getDesignName(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    productItemsMapper.mapper2(productItemsService.productItemsFindById(entity.getProductItems().getId()).get()) ,
				    entity.getOrderItem() != null ? orderItemsMapper.mapper(entity.getOrderItem()) : null
			);
	}
	public CustomsDTOS mapperToViewCart(Customs entity) {
		return new CustomsDTOS(
				entity.getId(),
				entity.getAccount().getId(),
				entity.getCanvasJson(),
				entity.getImageUrl(),
				entity.getDesignName(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				productItemsMapper.mapper2(entity.getProductItems()),
				entity.getOrderItem() != null ? orderItemsMapper.mapper(entity.getOrderItem()) : null
		);
	}
	public Customs mapperToObject(CustomsDTOS entity) {
		Accounts account = new Accounts();
		account.setId(entity.getAccountId());
		return new Customs(
					entity.getId(),
					account,
					entity.getCanvasJson(),
					entity.getImageUrl(),
					entity.getDesignName(),
					entity.getCreatedAt(),
					entity.getUpdatedAt(),
					productItemsService.productItemsFindById(entity.getProductItems().getId()).orElse(null),
				entity.getOrderItem() != null ? orderItemsMapper.mapperToObject(entity.getOrderItem()) : null
		);
	}
	
}
