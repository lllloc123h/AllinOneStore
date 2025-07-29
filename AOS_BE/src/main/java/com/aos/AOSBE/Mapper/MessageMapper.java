package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class MessageMapper {
	@Autowired
	private AccountsService accountsService;
	
	public MessageDTOS mapper(Message entity) {
		return new MessageDTOS(
				    entity.getId(),
				    entity.getKeyMessage(),
				    entity.getNotification(),
				    entity.getAccounts().getId()
			);
	}
	public Message mapperToObject(MessageDTOS entity) {
		return new Message(
					entity.getId(),
					entity.getKeyMessage(),
					entity.getNotification(),
					accountsService.accountsFindById(entity.getAccounts()).orElse(null)
			);
	}
	
}
