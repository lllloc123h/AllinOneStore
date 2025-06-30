package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.aos.AOSBE.Entity.Cancels;
import com.aos.AOSBE.Repository.CancelsRepository;

import jakarta.transaction.Transactional;

@Service
public class CancelsService {
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private CancelsRepository cancelsRepository;

	public List<Cancels> cancelsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Cancels> spec = specBuilder.buildFilter(filters);
		return cancelsRepository.findAll(spec, pageable).getContent();
	}

	@Transactional
	public Cancels cancelsSave(Cancels cancels) {
		return cancelsRepository.save(cancels);
	}

	public Optional<Cancels> cancelsFindById(int id) {
		return cancelsRepository.findById(id);
	}

	@Transactional
	public void cancelsDeleteById(int id) {
		cancelsRepository.deleteById(id);
	}
}