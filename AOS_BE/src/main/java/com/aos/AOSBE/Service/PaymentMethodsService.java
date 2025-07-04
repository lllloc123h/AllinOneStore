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

import com.aos.AOSBE.Entity.PaymentMethods;
import com.aos.AOSBE.Repository.PaymentMethodsRepository;

@Service
public class PaymentMethodsService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private PaymentMethodsRepository paymentMethodsRepository;

	public Page<PaymentMethods> paymentMethodsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<PaymentMethods> spec = specBuilder.buildFilter(filters);
		return paymentMethodsRepository.findAll(spec, pageable);
	}

	@Transactional
	public PaymentMethods paymentMethodsSave(PaymentMethods paymentMethods) {
		return paymentMethodsRepository.save(paymentMethods);
	}

	public Optional<PaymentMethods> paymentMethodsFindById(int id) {
		return paymentMethodsRepository.findById(id);
	}

	@Transactional
	public void paymentMethodsDeleteById(int id) {
		paymentMethodsRepository.deleteById(id);
	}
}