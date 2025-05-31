package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class CancelsMapper {
	public CancelsDTOS mapper(Cancels entity) {
		return new CancelsDTOS(
				    entity.id,
				    entity.reason,
				    entity.isPaid,
				    entity.status,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
