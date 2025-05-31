package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class ReturnsMapper {
	public ReturnsDTOS mapper(Returns entity) {
		return new ReturnsDTOS(
				    entity.id,
				    entity.reason,
				    entity.image1,
				    entity.image2,
				    entity.image3,
				    entity.isReturnedMoney,
				    entity.isReturnedItem,
				    entity.status,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
