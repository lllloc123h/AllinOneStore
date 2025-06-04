package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthoritiesMapper {
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private RolesService rolesService;
	
	public AuthoritiesDTOS mapper(Authorities entity) {
		return new AuthoritiesDTOS(
				    entity.getId(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getAccounts().getId(),
				    entity.getRoles().getId()
			);
	}
	public Authorities mapperToObject(AuthoritiesDTOS entity) {
		return new Authorities(
					entity.getId(),
					entity.getCreatedAt(),
					entity.getUpdatedAt(),
					accountsService.accountsFindById(entity.getAccounts()).orElse(null),
					rolesService.rolesFindById(entity.getRoles()).orElse(null)
			);
	}
	
}
