package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public Page<Object[]> baseProductsFindAllAsDto(int page, int size, String searchByKeyWord, int searchByCategory) {
		{
			Pageable pageable = PageRequest.of(page, size);
			return baseProductsRepository.findAllWithListPriceRaw(pageable, searchByKeyWord, searchByCategory);
		}
	}
}