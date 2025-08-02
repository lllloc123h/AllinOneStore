package com.aos.AOSBE.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.AuthoritiesDTOS;
import com.aos.AOSBE.Entity.Authorities;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.AuthoritiesService;
import com.aos.AOSBE.Service.RolesService;

@Component
public class AuthoritiesMapper {
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private AuthoritiesService authoritiesService;

	public AuthoritiesDTOS mapper(Authorities entity) {
		return new AuthoritiesDTOS(entity.getId(), entity.getCreatedAt(), entity.getUpdatedAt(),
				entity.getAccounts().getEmail(),
				authoritiesService.authoritiesFindRolesByAccountsEmail(entity.getAccounts().getEmail()));
	}

	public Authorities mapperToObject(AuthoritiesDTOS entity) {
		return new Authorities(entity.getId(), entity.getCreatedAt(), entity.getUpdatedAt(),
				accountsService.accountsFindByEmail(entity.getAccounts()).orElse(null),
				rolesService.rolesFindById(Integer.parseInt(entity.getRoles())).orElse(null));
	}

}
