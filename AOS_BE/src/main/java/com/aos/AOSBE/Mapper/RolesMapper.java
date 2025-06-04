package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class RolesMapper {
	
	public RolesDTOS mapper(Roles entity) {
		return new RolesDTOS(
				    entity.getId(),
				    entity.getName(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt()
			);
	}
	public Roles mapperToObject(RolesDTOS entity) {
		return new Roles(
					entity.getId(),
					entity.getName(),
					entity.getCreatedAt(),
					entity.getUpdatedAt()
			);
	}
	
}
