package com.aos.AOSBE.Mapper;

import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.AccountsDTOS;
import com.aos.AOSBE.Entity.Accounts;

@Component
public class AccountsMapper {

	public AccountsDTOS mapper(Accounts entity) {
		return new AccountsDTOS(entity.getId(), entity.getEmail(), entity.getPassword(), entity.isGender(),
				entity.getBirthday(), entity.getFullname(), entity.getAvatarUrl(), entity.getPhone(),
				entity.getAverageOrderValue(), entity.getUserRank(), entity.getTotalSpent(), entity.getTotalOrder(),
				entity.getLoyaltyPoint(), entity.getCreatedAt(), entity.getUpdatedAt());
	}

	public Accounts mapperToObject(AccountsDTOS entity) {
		return new Accounts(entity.getId(), entity.getEmail(), entity.getPassword(), entity.isGender(),
				entity.getBirthday(), entity.getFullname(), entity.getAvatarUrl(), entity.getPhone(),
				entity.getAverageOrderValue(), entity.getUserRank(), entity.getTotalSpent(), entity.getTotalOrder(),
				entity.getLoyaltyPoint(), entity.getCreatedAt(), entity.getUpdatedAt());
	}

}
