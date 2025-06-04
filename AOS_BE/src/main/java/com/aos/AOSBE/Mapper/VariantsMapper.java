package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class VariantsMapper {
	
	public VariantsDTOS mapper(Variants entity) {
		return new VariantsDTOS(
				    entity.getId(),
				    entity.getName(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt()
			);
	}
	public Variants mapperToObject(VariantsDTOS entity) {
		return new Variants(
					entity.getId(),
					entity.getName(),
					entity.getCreatedAt(),
					entity.getUpdatedAt()
			);
	}
	
}
