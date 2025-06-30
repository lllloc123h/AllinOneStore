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

import com.aos.AOSBE.Entity.PriceHistories;
import com.aos.AOSBE.Repository.PriceHistoriesRepository;

@Service
public class PriceHistoriesService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private PriceHistoriesRepository priceHistoriesRepository;

	public List<PriceHistories> priceHistoriesFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<PriceHistories> spec = specBuilder.buildFilter(filters);
		return priceHistoriesRepository.findAll(spec, pageable).getContent();
	}

	@Transactional
	public PriceHistories priceHistoriesSave(PriceHistories priceHistories) {
		return priceHistoriesRepository.save(priceHistories);
	}

	public Optional<PriceHistories> priceHistoriesFindById(int id) {
		return priceHistoriesRepository.findById(id);
	}

	@Transactional
	public void priceHistoriesDeleteById(int id) {
		priceHistoriesRepository.deleteById(id);
	}
}