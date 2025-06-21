package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.ReviewsDTOS;
import com.aos.AOSBE.Entity.Reviews;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.ProductItemsService;

@Component
public class ReviewsMapper {
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private ProductItemsService productItemsService;

	public ReviewsDTOS mapper(Reviews entity) {
		return new ReviewsDTOS(entity.getId(), entity.getRating(), entity.getComment(), entity.getImagesUrl(),
				entity.getCreatedAt(), entity.getAccounts().getId(), entity.getProductItems().getId());
	}

	public Reviews mapperToObject(ReviewsDTOS entity) {
		return new Reviews(entity.getId(), entity.getRating(), entity.getComment(), entity.getImages(),
				entity.getCreatedAt(), accountsService.accountsFindById(entity.getAccounts()).orElse(null),
				productItemsService.productItemsFindById(entity.getProductItems()).orElse(null));
	}

}
