package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class NewsMapper {
	public NewsDTOS mapper(News entity) {
		return new NewsDTOS(
				    entity.getId(),
				    entity.getImage(),
				    entity.isHome(),
				    entity.getDescription(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt()
			);
	}
}
