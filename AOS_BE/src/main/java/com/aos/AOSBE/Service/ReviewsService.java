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

import com.aos.AOSBE.Entity.Reviews;
import com.aos.AOSBE.Repository.ReviewsRepository;

@Service
public class ReviewsService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private ReviewsRepository reviewsRepository;

	public List<Reviews> reviewsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Reviews> spec = specBuilder.buildFilter(filters);
		return reviewsRepository.findAll(spec, pageable).getContent();
	}

	@Transactional
	public Reviews reviewsSave(Reviews reviews) {
		return reviewsRepository.save(reviews);
	}

	public Optional<Reviews> reviewsFindById(int id) {
		return reviewsRepository.findById(id);
	}

	@Transactional
	public void reviewsDeleteById(int id) {
		reviewsRepository.deleteById(id);
	}
}