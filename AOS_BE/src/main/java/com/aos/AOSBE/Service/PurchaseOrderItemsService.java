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

import com.aos.AOSBE.Entity.PurchaseOrderItems;
import com.aos.AOSBE.Repository.PurchaseOrderItemsRepository;

@Service
public class PurchaseOrderItemsService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private PurchaseOrderItemsRepository purchaseOrderItemsRepository;

	public List<PurchaseOrderItems> purchaseOrderItemsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<PurchaseOrderItems> spec = specBuilder.buildFilter(filters);
		return purchaseOrderItemsRepository.findAll(spec, pageable).getContent();
	}

	@Transactional
	public PurchaseOrderItems purchaseOrderItemsSave(PurchaseOrderItems purchaseOrderItems) {
		return purchaseOrderItemsRepository.save(purchaseOrderItems);
	}

	public Optional<PurchaseOrderItems> purchaseOrderItemsFindById(int id) {
		return purchaseOrderItemsRepository.findById(id);
	}

	@Transactional
	public void purchaseOrderItemsDeleteById(int id) {
		purchaseOrderItemsRepository.deleteById(id);
	}
}