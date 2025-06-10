package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.ProductItemsRepository;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class BaseProductsMapper {
	@Autowired
	private CategoriesService categoriesService;
	@Autowired
	private ProductItemsRepository productItemsRepository;
	
	public BaseProductsDTOS mapper(BaseProducts entity) {
		return new BaseProductsDTOS(
				    entity.getId(),
				    entity.getName(),
				    entity.getMaterial(),
				    entity.getMainImage(),
				    entity.isCustom(),
				    entity.getTurnBuy(),
				    entity.getRating(),
				    entity.isPromote(),
				    entity.isActive(),
				    productItemsRepository.findMinAndMaxPriceByBaseId(entity.getId())
			);
	}
	public BaseProducts mapperToObject(BaseProductsDTOS entity) {
		return new BaseProducts(
					entity.getId(),
					entity.getName(),
					entity.getMaterial(),
					entity.getMainImage(),
					entity.isCustom(),
					entity.getTurnBuy(),
					entity.getRating(),
					entity.isPromote(),
					entity.isActive(),
				null,null,null
			);
	}
	
}
