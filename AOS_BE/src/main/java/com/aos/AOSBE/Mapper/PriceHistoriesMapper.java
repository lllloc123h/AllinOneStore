package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class PriceHistoriesMapper {
	public PriceHistoriesDTOS mapper(PriceHistories entity) {
		return new PriceHistoriesDTOS(
				    entity.id,
				    entity.price,
				    entity.createdAt,
			);
	}
}
