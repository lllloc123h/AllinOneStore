package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class TraceXeActionMapMapper {
	public TraceXeActionMapDTOS mapper(TraceXeActionMap entity) {
		return new TraceXeActionMapDTOS(
				    entity.traceColumnId,
				    entity.packageName,
				    entity.xeActionName,
			);
	}
}
