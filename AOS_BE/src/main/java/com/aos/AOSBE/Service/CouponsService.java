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

import com.aos.AOSBE.Entity.Coupons;
import com.aos.AOSBE.Repository.CouponsRepository;

@Service
public class CouponsService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private CouponsRepository couponsRepository;

	public Page<Coupons> couponsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Coupons> spec = specBuilder.buildFilter(filters);
		return couponsRepository.findAll(spec, pageable);
	}

	@Transactional
	public Coupons couponsSave(Coupons coupons) {
		return couponsRepository.save(coupons);
	}

	public Optional<Coupons> couponsFindById(int id) {
		return couponsRepository.findById(id);
	}

	@Transactional
	public void couponsDeleteById(int id) {
		couponsRepository.deleteById(id);
	}
}