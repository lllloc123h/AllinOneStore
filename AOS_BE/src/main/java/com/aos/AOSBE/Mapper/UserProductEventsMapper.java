package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class UserProductEventsMapper {
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private ProductItemsService productItemsService;
	
	public UserProductEventsDTOS mapper(UserProductEvents entity) {
		return new UserProductEventsDTOS(
				    entity.getId(),
				    entity.getEventType(),
				    entity.getEventTime(),
				    entity.getSessionId(),
				    entity.getDevice(),
				    entity.getIpAddress(),
				    entity.getUserAgent(),
				    entity.getPositionInList(),
				    entity.getTimeSpentSeconds(),
				    entity.getReferrer(),
				    entity.getAccounts().getId(),
				    entity.getProductItems().getId()
			);
	}
	public UserProductEvents mapperToObject(UserProductEventsDTOS entity) {
		return new UserProductEvents(
					entity.getId(),
					entity.getEventType(),
					entity.getEventTime(),
					entity.getSessionId(),
					entity.getDevice(),
					entity.getIpAddress(),
					entity.getUserAgent(),
					entity.getPositionInList(),
					entity.getTimeSpentSeconds(),
					entity.getReferrer(),
					accountsService.accountsFindById(entity.getAccounts()).orElse(null),
					productItemsService.productItemsFindById(entity.getProductItems()).orElse(null)
			);
	}
	
}
