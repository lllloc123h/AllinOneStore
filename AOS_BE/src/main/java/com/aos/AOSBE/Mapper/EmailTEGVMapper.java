package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class EmailTEGVMapper {
	
	public EmailTEGVDTOS mapper(EmailTEGV entity) {
		return new EmailTEGVDTOS(
				    entity.getId(),
				    entity.getNameTEGV(),
				    entity.getEmailTEGV()
			);
	}
	public EmailTEGV mapperToObject(EmailTEGVDTOS entity) {
		return new EmailTEGV(
					entity.getId(),
					entity.getNameTEGV(),
					entity.getEmailTEGV()
			);
	}
	
}
