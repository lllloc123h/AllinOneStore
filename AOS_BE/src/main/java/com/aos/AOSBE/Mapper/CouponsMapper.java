package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class CouponsMapper {
	
	public CouponsDTOS mapper(Coupons entity) {
		return new CouponsDTOS(
				    entity.getId(),
				    entity.getCode(),
				    entity.getDescription(),
				    entity.getDiscountType(),
				    entity.getDiscountValue(),
				    entity.getMinOrderAmount(),
				    entity.getMaxDiscountAmount(),
				    entity.getUsageLimit(),
				    entity.getUsagePerCustomer(),
				    entity.allowVoucher(),
				    entity.active(),
				    entity.getCustomerGroup(),
				    entity.getStartAt(),
				    entity.getEndAt(),
				    entity.getCreatedAt(),
				    entity.getUpdatedAt()
			);
	}
	public Coupons mapperToObject(CouponsDTOS entity) {
		return new Coupons(
					entity.getId(),
					entity.getCode(),
					entity.getDescription(),
					entity.getDiscountType(),
					entity.getDiscountValue(),
					entity.getMinOrderAmount(),
					entity.getMaxDiscountAmount(),
					entity.getUsageLimit(),
					entity.getUsagePerCustomer(),
					entity.allowVoucher(),
					entity.active(),
					entity.getCustomerGroup(),
					entity.getStartAt(),
					entity.getEndAt(),
					entity.getCreatedAt(),
					entity.getUpdatedAt()
			);
	}
	
}
