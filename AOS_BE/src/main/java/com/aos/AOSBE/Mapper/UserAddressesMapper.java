package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class UserAddressesMapper {
	public UserAddressesDTOS mapper(UserAddresses entity) {
		return new UserAddressesDTOS(
				    entity.getId(),
				    entity.getRecipientName(),
				    entity.getPhone(),
				    entity.getProvince(),
				    entity.getDistrict(),
				    entity.getWard(),
				    entity.getStreet(),
				    entity.getLabel(),
				    entity.isdefault(),
				    entity.getNote(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt(),
				    entity.getAccounts().getId()
			);
	}
}
