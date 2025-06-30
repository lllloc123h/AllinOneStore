package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.Promotions;
import com.aos.AOSBE.Repository.PromotionsRepository;

@Service
public class PromotionsService {
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private PromotionsRepository promotionsRepository;

	public List<Promotions> promotionsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Promotions> spec = specBuilder.buildFilter(filters);
		return promotionsRepository.findAll(spec, pageable).getContent();
	}

	@Transactional
	public Promotions promotionsSave(Promotions promotions) {
		return promotionsRepository.save(promotions);
	}

	public Optional<Promotions> promotionsFindById(int id) {
		return promotionsRepository.findById(id);
	}

	@Transactional
	public void promotionsDeleteById(int id) {
		promotionsRepository.deleteById(id);
	}
}