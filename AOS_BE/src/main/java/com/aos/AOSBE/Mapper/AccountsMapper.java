package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountsMapper {
	
	public AccountsDTOS mapper(Accounts entity) {
		return new AccountsDTOS(
				    entity.getId(),
				    entity.getEmail(),
				    entity.getPassword(),
				    entity.getFullname(),
				    entity.getAvatar(),
				    entity.getPhone(),
				    entity.getAverageOrderValue(),
				    entity.getUserRank(),
				    entity.getTotalSpent(),
				    entity.getTotalOrder(),
				    entity.getLoyaltyPoint(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt()
			);
	}
	public Accounts mapperToObject(AccountsDTOS entity) {
		return new Accounts(
					entity.getId(),
					entity.getEmail(),
					entity.getPassword(),
					entity.getFullname(),
					entity.getAvatar(),
					entity.getPhone(),
					entity.getAverageOrderValue(),
					entity.getUserRank(),
					entity.getTotalSpent(),
					entity.getTotalOrder(),
					entity.getLoyaltyPoint(),
					entity.getCreatedAt(),
					entity.getUpdatedAt()
			);
	}
	
}
