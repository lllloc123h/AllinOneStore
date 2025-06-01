package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthoritiesMapper {
	
	public AuthoritiesDTOS mapper(Authorities entity) {
		return new AuthoritiesDTOS(
				    entity.getId(),
				    entity.getName(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt()
			);
	}
	public Authorities mapperToObject(AuthoritiesDTOS entity) {
		return new Authorities(
					entity.getId(),
					entity.getName(),
					entity.getCreatedAt(),
					entity.getUpdatedAt()
			);
	}
	
}
