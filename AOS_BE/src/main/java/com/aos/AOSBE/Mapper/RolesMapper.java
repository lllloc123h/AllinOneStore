package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class RolesMapper {
	public RolesDTOS mapper(Roles entity) {
		return new RolesDTOS(
				    entity.getId(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getAccounts().getId(),
				    entity.getAuthorities().getId()
			);
	}
}
