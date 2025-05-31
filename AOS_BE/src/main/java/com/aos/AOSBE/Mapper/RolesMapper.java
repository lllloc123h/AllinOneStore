package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class RolesMapper {
	public RolesDTOS mapper(Roles entity) {
		return new RolesDTOS(
				    entity.id,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
