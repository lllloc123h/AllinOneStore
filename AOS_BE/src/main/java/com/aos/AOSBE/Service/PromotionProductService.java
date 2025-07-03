package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.PromotionProduct;
import com.aos.AOSBE.Repository.PromotionProductRepository;

@Service
public class PromotionProductService {
	@Autowired
	private PromotionProductRepository promotionProductRepository;

	public List<PromotionProduct> promotionProductFindAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return promotionProductRepository.findAll(pageable).getContent();
	}

	@Transactional
	public PromotionProduct promotionProductSave(PromotionProduct promotionProduct) {
		return promotionProductRepository.save(promotionProduct);
	}

	public Optional<PromotionProduct> promotionProductFindById(int id) {
		return promotionProductRepository.findById(id);
	}

	@Transactional
	public void promotionProductDeleteById(int id) {
		promotionProductRepository.deleteById(id);
	}

	@Transactional
	public List<PromotionProduct> promotionProductFindByProductItemId(int productItemId) {
		return promotionProductRepository.findByProductItems_Id(productItemId);
	}
}