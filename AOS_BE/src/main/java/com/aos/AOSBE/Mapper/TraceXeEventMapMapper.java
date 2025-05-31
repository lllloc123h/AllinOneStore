package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class TraceXeEventMapMapper {
	public TraceXeEventMapDTOS mapper(TraceXeEventMap entity) {
		return new TraceXeEventMapDTOS(
				    entity.getTraceEventId(),
				    entity.getPackageName(),
				    entity.getXeEventName()
			);
	}
}
