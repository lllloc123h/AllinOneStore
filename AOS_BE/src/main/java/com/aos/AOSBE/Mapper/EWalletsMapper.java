package com.aos.AOSBE.Mapper;

import com.aos.AOSBE.Repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.EWalletsDTOS;
import com.aos.AOSBE.Entity.EWallets;
import com.aos.AOSBE.Service.AccountsService;

@Component
public class EWalletsMapper {
	@Autowired
	private AccountsRepository accountsRepository;

	public EWalletsDTOS mapper(EWallets entity) {
		return new EWalletsDTOS(entity.getId(), entity.getBalance(), entity.getWalletType(), entity.isActive(),
				entity.getCodeActivce(), entity.getCreatedAt(), entity.getAccounts().getEmail());
	}

	public EWallets mapperToObject(EWalletsDTOS entity) {
		return new EWallets(entity.getId(), entity.getBalance(), entity.getWalletType(), entity.isActive(),
				entity.getCodeActivce(), entity.getCreatedAt(),
				accountsRepository.findByEmail(entity.getAccounts()).orElse(null));
	}

}
