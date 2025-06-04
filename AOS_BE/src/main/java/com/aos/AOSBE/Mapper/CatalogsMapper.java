package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CatalogsMapper {
	
	public CatalogsDTOS mapper(Catalogs entity) {
		return new CatalogsDTOS(
				    entity.getId(),
				    entity.getName(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt()
			);
	}
	public Catalogs mapperToObject(CatalogsDTOS entity) {
		return new Catalogs(
					entity.getId(),
					entity.getName(),
					entity.getCreatedAt(),
					entity.getUpdatedAt()
			);
	}
	
}
