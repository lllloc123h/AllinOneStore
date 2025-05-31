package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class CostHistoriesMapper {
	public CostHistoriesDTOS mapper(CostHistories entity) {
		return new CostHistoriesDTOS(
				    entity.id,
				    entity.cost,
				    entity.createdAt,
			);
	}
}
