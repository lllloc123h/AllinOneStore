package com.aos.AOSBE.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.OrderItems;
import com.aos.AOSBE.Repository.OrderItemsRepository;

@Service
public class OrderItemsService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private OrderItemsRepository orderItemsRepository;

	public Page<OrderItems> orderItemsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<OrderItems> spec = specBuilder.buildFilter(filters);
		return orderItemsRepository.findAll(spec, pageable);
	}

	@Transactional
	public OrderItems orderItemsSave(OrderItems orderItems) {
		return orderItemsRepository.save(orderItems);
	}

	public Optional<OrderItems> orderItemsFindById(int id) {
		return orderItemsRepository.findById(id);
	}

	@Transactional
	public void orderItemsDeleteById(int id) {
		orderItemsRepository.deleteById(id);
	}

	@Transactional
	public void orderItemsSaveAll(List<OrderItems> listToSave) {
		orderItemsRepository.saveAll(listToSave);
	}

	@Transactional
	public List<OrderItems> findByOrderId(int orderId) {
		return orderItemsRepository.findByOrdersId(orderId);
	}
	public Long sumQuantityByProductId(Integer productItemId, LocalDateTime createdAt) {
		return orderItemsRepository.sumQuantityByProductId(productItemId, createdAt);
	}
	public Double sumTotalByProductId(Integer productItemId) {
		return orderItemsRepository.sumTotalByProductItemId(productItemId);
	}
	public Double sumCostAtBuyTime(Integer productItemId) {
		return orderItemsRepository.sumCostAtBuyByProductItemId(productItemId);
	}
	//
	public Double revenueProductItemByStatus(String shippingStatus, String paymentStatus, int productItemId) {
		return orderItemsRepository.revenueProductItemByStatus(shippingStatus, paymentStatus, productItemId);
	}
	public Double costProductItemByStatus(String shippingStatus, String paymentStatus, int productItemId) {
		return orderItemsRepository.costProductItemByStatus(shippingStatus, paymentStatus, productItemId);
	}
	public Double discountProductItemByStatus(String shippingStatus, String paymentStatus, int productItemId) {
		return orderItemsRepository.discountProductItemByStatus(shippingStatus, paymentStatus, productItemId);
	}
	public Integer countProductItemByStatus(String shippingStatus, String paymentStatus, int productItemId) {
		return orderItemsRepository.countProductItemByStatus(shippingStatus, paymentStatus, productItemId);
	}
}