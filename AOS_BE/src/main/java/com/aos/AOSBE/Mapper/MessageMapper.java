package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.MessageDTOS;
import com.aos.AOSBE.Entity.Message;
import com.aos.AOSBE.Service.AccountsService;

@Component
public class MessageMapper {
	@Autowired
	private AccountsService accountsService;

	public MessageDTOS mapper(Message entity) {
		return new MessageDTOS(entity.getId(), entity.getKeyMessage(), entity.getNotification(), entity.getCreatedAt(),
				entity.getAccounts().getEmail());
	}

	public Message mapperToObject(MessageDTOS entity) {
		return new Message(entity.getId(), entity.getKeyMessage(), entity.getNotification(), entity.getCreatedAt(),
				accountsService.accountsFindByEmail(entity.getAccounts()).orElse(null));
	}

}
