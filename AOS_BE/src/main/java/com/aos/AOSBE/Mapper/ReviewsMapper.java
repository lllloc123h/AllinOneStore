package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class ReviewsMapper {
	public ReviewsDTOS mapper(Reviews entity) {
		return new ReviewsDTOS(
				    entity.id,
				    entity.rating,
				    entity.comment,
				    entity.images,
				    entity.createdAt,
			);
	}
}
