package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class PaymentMethodsMapper {
	
	public PaymentMethodsDTOS mapper(PaymentMethods entity) {
		return new PaymentMethodsDTOS(
				    entity.getId(),
				    entity.getName(),
				    entity.getDescription(),
				    entity.isActive(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt()
			);
	}
	public PaymentMethods mapperToObject(PaymentMethodsDTOS entity) {
		return new PaymentMethods(
					entity.getId(),
					entity.getName(),
					entity.getDescription(),
					entity.isActive(),
					entity.getCreatedAt(),
					entity.getUpdatedAt()
			);
	}
	
}
