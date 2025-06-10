package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class EWalletTransactionsMapper {
	@Autowired
	private EWalletsService eWalletsService;
	
	public EWalletTransactionsDTOS mapper(EWalletTransactions entity) {
		return new EWalletTransactionsDTOS(
				    entity.getId(),
				    entity.getAmount(),
				    entity.getTransactionType(),
				    entity.getRelatedWalletId(),
				    entity.getDescription(),
				    entity.getCreatedAt(),
				    entity.getEWallets().getId()
			);
	}
	public EWalletTransactions mapperToObject(EWalletTransactionsDTOS entity) {
		return new EWalletTransactions(
					entity.getId(),
					entity.getAmount(),
					entity.getTransactionType(),
					entity.getRelatedWalletId(),
					entity.getDescription(),
					entity.getCreatedAt(),
					eWalletsService.eWalletsFindById(entity.getEWallets()).orElse(null)
			);
	}
	
}
