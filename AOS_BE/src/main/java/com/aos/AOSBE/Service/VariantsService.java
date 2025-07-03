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

import com.aos.AOSBE.Entity.Variants;
import com.aos.AOSBE.Repository.VariantsRepository;

@Service
public class VariantsService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private VariantsRepository variantsRepository;

	public Page<Variants> variantsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Variants> spec = specBuilder.buildFilter(filters);
		return variantsRepository.findAll(spec, pageable);
	}

	@Transactional
	public Variants variantsSave(Variants variants) {
		return variantsRepository.save(variants);
	}

	public Optional<Variants> variantsFindById(int id) {
		return variantsRepository.findById(id);
	}

	@Transactional
	public void variantsDeleteById(int id) {
		variantsRepository.deleteById(id);
	}
}