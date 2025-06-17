package com.aos.AOSBE.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.DTOS.FilterResponseDTOS;
import com.aos.AOSBE.Entity.BaseProducts;
import com.aos.AOSBE.Mapper.BaseProductsMapper;
import com.aos.AOSBE.Repository.BaseProductsRepository;

@Service
public class BaseProductsService {
	@Autowired
	private BaseProductsRepository baseProductsRepository;

	@Autowired
	private BaseProductsMapper baseProductsMapper;

	public List<BaseProducts> baseProductsFindAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return baseProductsRepository.findAll(pageable).getContent();
	}

	@Transactional
	public BaseProducts baseProductsSave(BaseProducts baseProducts) {
		return baseProductsRepository.save(baseProducts);
	}

	public Optional<BaseProducts> baseProductsFindById(int id) {
		return baseProductsRepository.findById(id);
	}

	@Transactional
	public void baseProductsDeleteById(int id) {
		baseProductsRepository.deleteById(id);
	}

//    public List<BaseProductsDTOS> baseProductsFindByIsActiveTrue(int page,int size,String category, String name,String sku, double minPrice,double maxPrice){ {
//        Pageable pageable = PageRequest.of(page, size);
//        return baseProductsRepository.findBaseProductsByCategoryAndNameAndSkuAndPrice(pageable,category,name,sku,minPrice,maxPrice).stream()
//                .map(base -> baseProductsMapper.mapper(base))
//                .toList();
//    }
	public List<FilterResponseDTOS> baseProductsFindAllAsDto(int page, int size) {
		{
			Pageable pageable = PageRequest.of(page, size);
			List<FilterResponseDTOS> FilterResponseMapped = new ArrayList<>();
			List<Object[]> FilterResponse = baseProductsRepository.findAllWithListPriceRaw(pageable).getContent();
			FilterResponse.forEach(e -> {
				FilterResponseDTOS item = new FilterResponseDTOS();
				item.setId((int) e[0]);
				item.setName((String) e[1]);
				item.setMaterial((String) e[2]);
				item.setMainImage((String) e[3]);
				item.setCustom((Boolean) e[4]);
				item.setTurnBuy((Integer) e[5]);
				item.setRating((Integer) e[6]);
				item.setDescription((String) e[7]);
				item.setActive((Boolean) e[8]);
				item.setSafetyStock((Integer) e[9]);
				item.setQty(((Number) e[10]).intValue()); // handle BigInteger
				item.setListPriceRaw((String) e[11]);
				FilterResponseMapped.add(item);
			});
			return FilterResponseMapped;
//      return baseProductsRepository.findAllAsDto(pageable,category,name,sku,minPrice,maxPrice);
//    		  .stream()
//              .map(base -> baseProductsMapper.mapper(base))
//              .toList();
		}
	}
}