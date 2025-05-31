package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;


public class CouponsMapper {
	public CouponsDTOS mapper(Coupons entity) {
		return new CouponsDTOS(
				    entity.id,
				    entity.code,
				    entity.description,
				    entity.discountType,
				    entity.discountValue,
				    entity.minOrderAmount,
				    entity.maxDiscountAmount,
				    entity.usageLimit,
				    entity.usagePerCustomer,
				    entity.allowVoucher,
				    entity.active,
				    entity.customerGroup,
				    entity.startAt,
				    entity.endAt,
				    entity.createdAt,
				    entity.updatedAt,
			);
	}
}
