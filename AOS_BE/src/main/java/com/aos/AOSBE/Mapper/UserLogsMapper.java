package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class UserLogsMapper {
	
	public UserLogsDTOS mapper(UserLogs entity) {
		return new UserLogsDTOS(
				    entity.getId(),
				    entity.getUserId(),
				    entity.getAction(),
				    entity.getDescription(),
				    entity.getIpAddress(),
				    entity.getUserAgent(),
				    entity.getCreatedAt(),
				    entity.getModule()
			);
	}
	public UserLogs mapperToObject(UserLogsDTOS entity) {
		return new UserLogs(
					entity.getId(),
					entity.getUserId(),
					entity.getAction(),
					entity.getDescription(),
					entity.getIpAddress(),
					entity.getUserAgent(),
					entity.getCreatedAt(),
					entity.getModule()
			);
	}
	
}
