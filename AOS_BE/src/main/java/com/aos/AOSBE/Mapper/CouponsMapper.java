package com.aos.AOSBE.Mapper;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import org.springframework.stereotype.Component;
@Component
public class CouponsMapper {
	
	public CouponsDTOS mapper(Coupons entity) {
		return new CouponsDTOS(
				    entity.getId(),
				    entity.getCode(),
				    entity.getDescription(),
				    entity.getDiscountType(),
				    entity.getDiscountValue(),
				    entity.getMinOrderAmount(),
				    entity.getQty(),
				    entity.getUsagePerCustomer(),
				    entity.isAllowVoucher(),
				    entity.isActive(),
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
					entity.getQty(),
					entity.getUsagePerCustomer(),
					entity.isAllowVoucher(),
					entity.isActive(),
					entity.getCustomerGroup(),
					entity.getStartAt(),
					entity.getEndAt(),
					entity.getCreatedAt(),
					entity.getUpdatedAt()
			);
	}
	
}
