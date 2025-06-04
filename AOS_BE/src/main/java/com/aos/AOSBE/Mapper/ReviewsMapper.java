package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class ReviewsMapper {
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private ProductItemsService productItemsService;
	
	public ReviewsDTOS mapper(Reviews entity) {
		return new ReviewsDTOS(
				    entity.getId(),
				    entity.getRating(),
				    entity.getComment(),
				    entity.getImages(),
				    entity.getCreatedAt(),
				    entity.getAccounts().getId(),
				    entity.getProductItems().getId()
			);
	}
	public Reviews mapperToObject(ReviewsDTOS entity) {
		return new Reviews(
					entity.getId(),
					entity.getRating(),
					entity.getComment(),
					entity.getImages(),
					entity.getCreatedAt(),
					accountsService.accountsFindById(entity.getAccounts()).orElse(null),
					productItemsService.productItemsFindById(entity.getProductItems()).orElse(null)
			);
	}
	
}
