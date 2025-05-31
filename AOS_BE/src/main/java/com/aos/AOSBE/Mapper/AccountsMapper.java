package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class AccountsMapper {
	public AccountsDTOS mapper(Accounts entity) {
		return new AccountsDTOS(
				    entity.id,
				    entity.email,
				    entity.password,
				    entity.fullname,
				    entity.avatar,
				    entity.phone,
				    entity.averageOrderValue,
				    entity.userRank,
				    entity.totalSpent,
				    entity.totalOrder,
				    entity.loyaltyPoint,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
