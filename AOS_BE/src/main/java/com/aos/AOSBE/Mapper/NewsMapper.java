package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class NewsMapper {
	public NewsDTOS mapper(News entity) {
		return new NewsDTOS(
				    entity.id,
				    entity.image,
				    entity.isHome,
				    entity.description,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
