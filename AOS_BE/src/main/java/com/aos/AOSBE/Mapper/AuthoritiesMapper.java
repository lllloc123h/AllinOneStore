package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class AuthoritiesMapper {
	public AuthoritiesDTOS mapper(Authorities entity) {
		return new AuthoritiesDTOS(
				    entity.id,
				    entity.name,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
