package com.aos.AOSBE.Mapper;

import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.NewsDTOS;
import com.aos.AOSBE.Entity.News;

@Component
public class NewsMapper {

	public NewsDTOS mapper(News entity) {
		return new NewsDTOS(entity.getId(), entity.getImageUrl(), entity.isHome(), entity.getDescription(),
				entity.getCreatedAt(), entity.getUpdatedAt());
	}

	public News mapperToObject(NewsDTOS entity) {
		return new News(entity.getId(), entity.getImageUrl(), entity.isHome(), entity.getDescription(),
				entity.getCreatedAt(), entity.getUpdatedAt());
	}

}
