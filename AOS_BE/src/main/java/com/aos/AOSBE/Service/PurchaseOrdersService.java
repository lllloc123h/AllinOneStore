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

import com.aos.AOSBE.Entity.PurchaseOrders;
import com.aos.AOSBE.Repository.PurchaseOrdersRepository;

@Service
public class PurchaseOrdersService {
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private PurchaseOrdersRepository purchaseOrdersRepository;

	public List<PurchaseOrders> purchaseOrdersFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<PurchaseOrders> spec = specBuilder.buildFilter(filters);
		return purchaseOrdersRepository.findAll(spec, pageable).getContent();
	}

	@Transactional
	public PurchaseOrders purchaseOrdersSave(PurchaseOrders purchaseOrders) {
		return purchaseOrdersRepository.save(purchaseOrders);
	}

	public Optional<PurchaseOrders> purchaseOrdersFindById(int id) {
		return purchaseOrdersRepository.findById(id);
	}

	@Transactional
	public void purchaseOrdersDeleteById(int id) {
		purchaseOrdersRepository.deleteById(id);
	}
}