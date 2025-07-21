package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.PromotionProducts;
import com.aos.AOSBE.Repository.PromotionProductsRepository;

@Service
public class PromotionProductsService {

	@Autowired
	private GenericSpecificationBuilder specBuilder;

	@Autowired
	private PromotionProductsRepository promotionProductsRepository;

	public Optional<PromotionProducts> findById(int id) {
		return promotionProductsRepository.findById(id);
	}

	@Transactional
	public PromotionProducts save(PromotionProducts entity) {
		return promotionProductsRepository.save(entity);
	}

	@Transactional
	public void deleteById(int id) {
		promotionProductsRepository.deleteById(id);
	}

	public List<PromotionProducts> findPromotionProductsByPromotionId(int promotionId) {
		return promotionProductsRepository.findPromotionProductsByPromotionId(promotionId);
	}

	public Page<PromotionProducts> promotionsFindAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		return promotionProductsRepository.findAll(pageable);
	}

	public List<PromotionProducts> findByProductItemsId(int productItemId) {
		return promotionProductsRepository.findByProductItems_Id(productItemId);
	}

	public Page<PromotionProducts> promotionsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<PromotionProducts> spec = specBuilder.buildFilter(filters);
		return promotionProductsRepository.findAll(spec, pageable);
	}
	public List<PromotionProducts> findPromotionProductsByPromotionsId(int promotionsId) {
		return promotionProductsRepository.findPromotionProductsByPromotions_Id(promotionsId);
	}
}
