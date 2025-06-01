package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class TraceXeEventMapMapper {
	
	public TraceXeEventMapDTOS mapper(TraceXeEventMap entity) {
		return new TraceXeEventMapDTOS(
				    entity.getTraceEventId(),
				    entity.getPackageName(),
				    entity.getXeEventName()
			);
	}
	public TraceXeEventMap mapperToObject(TraceXeEventMapDTOS entity) {
		return new TraceXeEventMap(
					entity.getTraceEventId(),
					entity.getPackageName(),
					entity.getXeEventName()
			);
	}
	
}
