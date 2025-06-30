package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class SysdiagramsMapper {
	
	public SysdiagramsDTOS mapper(Sysdiagrams entity) {
		return new SysdiagramsDTOS(
				    entity.getName(),
				    entity.getPrincipalId(),
				    entity.getDiagramId(),
				    entity.getVersion(),
				    entity.getDefinition()
			);
	}
	public Sysdiagrams mapperToObject(SysdiagramsDTOS entity) {
		return new Sysdiagrams(
					entity.getName(),
					entity.getPrincipalId(),
					entity.getDiagramId(),
					entity.getVersion(),
					entity.getDefinition()
			);
	}
	
}
