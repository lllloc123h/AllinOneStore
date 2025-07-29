package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.UserProductEventsDTOS;
import com.aos.AOSBE.Entity.UserProductEvents;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.ProductItemsService;

@Component
public class UserProductEventsMapper {
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private ProductItemsService productItemsService;

	public UserProductEventsDTOS mapper(UserProductEvents entity) {
		return new UserProductEventsDTOS(entity.getId(), entity.getEventType(), entity.getEventTime(),
				entity.getSessionId(), entity.getDevice(), entity.getIpAddress(), entity.getUserAgent(),
				entity.getPositionInList(), entity.getTimeSpentSeconds(), entity.getReferrer(),
				entity.getAccounts().getEmail(), entity.getProductItems().getId());
	}

	public UserProductEvents mapperToObject(UserProductEventsDTOS entity) {
		return new UserProductEvents(entity.getId(), entity.getEventType(), entity.getEventTime(),
				entity.getSessionId(), entity.getDevice(), entity.getIpAddress(), entity.getUserAgent(),
				entity.getPositionInList(), entity.getTimeSpentSeconds(), entity.getReferrer(),
				accountsService.accountsFindByEmailToCatchEvent(entity.getAccounts()),
				productItemsService.productItemsFindById(entity.getProductItems()).orElse(null));
	}

}
