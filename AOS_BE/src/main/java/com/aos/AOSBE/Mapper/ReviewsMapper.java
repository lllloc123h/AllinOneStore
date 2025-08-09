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
	@Autowired
	private OrdersService ordersService;
	
	public ReviewsDTOS mapper(Reviews entity) {
		Integer orderId = entity.getOrders() != null ? entity.getOrders().getId() : null;
		return new ReviewsDTOS(
				    entity.getId(),
				    entity.getRating(),
				    entity.getComment(),
				    entity.getImageUrl1(),
				    entity.getImageUrl2(),
				    entity.getImageUrl3(),
				    entity.getVideoUrl(),
				    entity.getCreatedAt(),
				    entity.getAccounts().getId(),
					entity.getAccounts().getFullname(),
				    entity.getProductItems().getId(),
					orderId
			);
	}
	public Reviews mapperToObject(ReviewsDTOS entity) {
		return new Reviews(
					entity.getId(),
					entity.getRating(),
					entity.getComment(),
					entity.getImageUrl1(),
					entity.getImageUrl2(),
					entity.getImageUrl3(),
					entity.getVideoUrl(),
					entity.getCreatedAt(),
					accountsService.accountsFindById(entity.getAccountId()).orElse(null),
					productItemsService.productItemsFindById(entity.getProductItems()).orElse(null),
					ordersService.ordersFindById(entity.getOrderId()).orElse(null)
			);
	}
	
}
