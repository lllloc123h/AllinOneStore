package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAddressesMapper {
	@Autowired
	private AccountsService accountsService;
	
	public UserAddressesDTOS mapper(UserAddresses entity) {
		return new UserAddressesDTOS(
				    entity.getId(),
				    entity.getRecipientName(),
				    entity.getPhone(),
				    entity.getProvince(),
				    entity.getDistrict(),
				    entity.getWard(),
				    entity.getStreet(),
				    entity.getLabel(),
				    entity.isIsdefault(),
				    entity.getNote(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getAccounts().getId()
			);
	}
	public UserAddresses mapperToObject(UserAddressesDTOS entity) {
		return new UserAddresses(
					entity.getId(),
					entity.getRecipientName(),
					entity.getPhone(),
					entity.getProvince(),
					entity.getDistrict(),
					entity.getWard(),
					entity.getStreet(),
					entity.getLabel(),
					entity.isDefault(),
					entity.getNote(),
					entity.getCreatedAt(),
					entity.getUpdatedAt(),
					accountsService.accountsFindById(entity.getAccounts()).orElse(null)
			);
	}
	
}
