package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class CustomMapper {
	public CustomDTOS mapper(Custom entity) {
		return new CustomDTOS(
				    entity.getId(),
				    entity.getInfor(),
				    entity.getProductItems().getId()
			);
	}
}
