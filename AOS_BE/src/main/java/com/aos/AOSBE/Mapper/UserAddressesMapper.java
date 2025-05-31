package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class UserAddressesMapper {
	public UserAddressesDTOS mapper(UserAddresses entity) {
		return new UserAddressesDTOS(
				    entity.id,
				    entity.recipientName,
				    entity.phone,
				    entity.province,
				    entity.district,
				    entity.ward,
				    entity.street,
				    entity.label,
				    entity.isdefault,
				    entity.note,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
