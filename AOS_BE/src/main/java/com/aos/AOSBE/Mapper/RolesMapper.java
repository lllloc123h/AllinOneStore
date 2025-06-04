package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class RolesMapper {
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private AuthoritiesService authoritiesService;
	
	public RolesDTOS mapper(Roles entity) {
		return new RolesDTOS(
				    entity.getId(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getAccounts().getId(),
				    entity.getAuthorities().getId()
			);
	}
	public Roles mapperToObject(RolesDTOS entity) {
		return new Roles(
					entity.getId(),
					entity.getCreatedAt(),
					entity.getUpdatedAt(),
					accountsService.accountsFindById(entity.getAccounts()).orElse(null),
					authoritiesService.authoritiesFindById(entity.getAuthorities()).orElse(null)
			);
	}
	
}
