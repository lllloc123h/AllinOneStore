package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class UserLogsMapper {
	public UserLogsDTOS mapper(UserLogs entity) {
		return new UserLogsDTOS(
				    entity.id,
				    entity.userId,
				    entity.action,
				    entity.description,
				    entity.ipAddress,
				    entity.userAgent,
				    entity.createdAt,
				    entity.module,
			);
	}
}
