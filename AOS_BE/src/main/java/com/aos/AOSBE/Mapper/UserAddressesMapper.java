package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.UserAddressesDTOS;
import com.aos.AOSBE.Entity.UserAddresses;
import com.aos.AOSBE.Service.AccountsService;

@Component
public class UserAddressesMapper {
	@Autowired
	private AccountsService accountsService;

	public UserAddressesDTOS mapper(UserAddresses entity) {
		return new UserAddressesDTOS(entity.getId(), entity.getRecipientName(), entity.getPhone(), entity.getProvince(),
				entity.getDistrict(), entity.getWard(), entity.getStreet(), entity.getLabel(), entity.isDefault(),
				entity.getNote(), entity.getCreatedAt(), entity.getUpdatedAt(), entity.getAccounts().getEmail());
	}

	public UserAddresses mapperToObject(UserAddressesDTOS entity) {
		return new UserAddresses(entity.getId(), entity.getRecipientName(), entity.getPhone(), entity.getProvince(),
				entity.getDistrict(), entity.getWard(), entity.getStreet(), entity.getLabel(), entity.isDefault(),
				entity.getNote(), entity.getCreatedAt(), entity.getUpdatedAt(),
				accountsService.accountsFindByEmail(entity.getAccounts()).orElse(null));
	}

}
