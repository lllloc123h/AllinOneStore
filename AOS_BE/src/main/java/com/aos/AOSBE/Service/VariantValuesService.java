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

import com.aos.AOSBE.Entity.VariantValues;
import com.aos.AOSBE.Repository.VariantValuesRepository;

@Service
public class VariantValuesService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private VariantValuesRepository variantValuesRepository;

	public Page<VariantValues> variantValuesFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<VariantValues> spec = specBuilder.buildFilter(filters);
		return variantValuesRepository.findAll(spec, pageable);
	}

	@Transactional
	public VariantValues variantValuesSave(VariantValues variantValues) {
		return variantValuesRepository.save(variantValues);
	}

	public Optional<VariantValues> variantValuesFindById(int id) {
		return variantValuesRepository.findById(id);
	}

	@Transactional
	public void variantValuesDeleteById(int id) {
		variantValuesRepository.deleteById(id);
	}
}