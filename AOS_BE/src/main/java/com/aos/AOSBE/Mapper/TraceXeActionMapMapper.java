package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class TraceXeActionMapMapper {
	
	public TraceXeActionMapDTOS mapper(TraceXeActionMap entity) {
		return new TraceXeActionMapDTOS(
				    entity.getTraceColumnId(),
				    entity.getPackageName(),
				    entity.getXeActionName()
			);
	}
	public TraceXeActionMap mapperToObject(TraceXeActionMapDTOS entity) {
		return new TraceXeActionMap(
					entity.getTraceColumnId(),
					entity.getPackageName(),
					entity.getXeActionName()
			);
	}
	
}
