package com.aos.AOSBE.Service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.Returns;
import com.aos.AOSBE.Repository.ReturnsRepository;

@Service
public class ReturnsService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private ReturnsRepository returnsRepository;

	public Page<Returns> returnsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Returns> spec = specBuilder.buildFilter(filters);
		return returnsRepository.findAll(spec, pageable);
	}

	@Transactional
	public Returns returnsSave(Returns returns) {
		return returnsRepository.save(returns);
	}

	public Optional<Returns> returnsFindById(int id) {
		return returnsRepository.findById(id);
	}

	@Transactional
	public void returnsDeleteById(int id) {
		returnsRepository.deleteById(id);
	}
	public Double findTotalRefundAmountByProductItemId(Integer productItemId) {
		return returnsRepository.findTotalRefundAmountByProductItemId(productItemId);
	}

}