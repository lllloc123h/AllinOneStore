package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class EWalletsMapper {
	@Autowired
	private AccountsService accountsService;
	
	public EWalletsDTOS mapper(EWallets entity) {
		return new EWalletsDTOS(
				    entity.getId(),
				    entity.getBalance(),
				    entity.getWalletType(),
				    entity.isActive(),
				    entity.getCreatedAt(),
				    entity.getAccounts().getId()
			);
	}
	public EWallets mapperToObject(EWalletsDTOS entity) {
		return new EWallets(
					entity.getId(),
					entity.getBalance(),
					entity.getWalletType(),
					entity.isActive(),
					entity.getCreatedAt(),
					accountsService.accountsFindById(entity.getAccounts()).orElse(null)
			);
	}
	
}
