package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.EWalletTransactionsDTOS;
import com.aos.AOSBE.Entity.EWalletTransactions;
import com.aos.AOSBE.Service.EWalletsService;

@Component
public class EWalletTransactionsMapper {
	@Autowired
	private EWalletsService eWalletsService;

	public EWalletTransactionsDTOS mapper(EWalletTransactions entity) {
		return new EWalletTransactionsDTOS(entity.getId(), entity.getAmount(), entity.getTransactionType(),
				entity.getOrderId(), entity.getStatus(), entity.getRelatedWalletId(), entity.getDescription(),
				entity.getCreatedAt(), entity.getEWallets().getId());
	}

	public EWalletTransactions mapperToObject(EWalletTransactionsDTOS entity) {
		return new EWalletTransactions(entity.getId(), entity.getAmount(), entity.getTransactionType(),
				entity.getOrderId(), entity.getStatus(), entity.getRelatedWalletId(), entity.getDescription(),
				entity.getCreatedAt(), eWalletsService.eWalletsFindById(entity.getEWallets()));
	}

}
