package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class BaseProductsMapper {
	public BaseProductsDTOS mapper(BaseProducts entity) {
		return new BaseProductsDTOS(
				    entity.id,
				    entity.name,
				    entity.material,
				    entity.mainImage,
				    entity.isCustom,
				    entity.turnBuy,
				    entity.rating,
				    entity.isPromote,
				    entity.isActive,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
