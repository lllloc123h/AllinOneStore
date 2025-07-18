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

import com.aos.AOSBE.Entity.ShippingMethods;
import com.aos.AOSBE.Repository.ShippingMethodsRepository;

@Service
public class ShippingMethodsService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private ShippingMethodsRepository shippingMethodsRepository;

	public Page<ShippingMethods> shippingMethodsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<ShippingMethods> spec = specBuilder.buildFilter(filters);
		return shippingMethodsRepository.findAll(spec, pageable);
	}

	@Transactional
	public ShippingMethods shippingMethodsSave(ShippingMethods shippingMethods) {
		return shippingMethodsRepository.save(shippingMethods);
	}

	public Optional<ShippingMethods> shippingMethodsFindById(int id) {
		return shippingMethodsRepository.findById(id);
	}

	public Optional<ShippingMethods> shippingMethodsFindByName(String name) {
		return shippingMethodsRepository.findByName(name);
	}

	@Transactional
	public void shippingMethodsDeleteById(int id) {
		shippingMethodsRepository.deleteById(id);
	}
}