package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class BankCodesMapper {
	
	public BankCodesDTOS mapper(BankCodes entity) {
		return new BankCodesDTOS(
				    entity.getId(),
				    entity.getBankCode(),
				    entity.getBankName(),
				    entity.getMomoNumericCode(),
				    entity.getLogoUrl()
			);
	}
	public BankCodes mapperToObject(BankCodesDTOS entity) {
		return new BankCodes(
					entity.getId(),
					entity.getBankCode(),
					entity.getBankName(),
					entity.getMomoNumericCode(),
					entity.getLogoUrl()
			);
	}
	
}
