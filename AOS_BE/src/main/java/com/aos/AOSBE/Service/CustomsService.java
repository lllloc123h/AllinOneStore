package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.aos.AOSBE.DTOS.CheckoutCustomDTO;
import com.aos.AOSBE.Entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.Customs;
import com.aos.AOSBE.Repository.CustomsRepository;

@Service
public class CustomsService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private CustomsRepository customsRepository;

	public Page<Customs> customsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Customs> spec = specBuilder.buildFilter(filters);
		return customsRepository.findAll(spec, pageable);
	}

	@Transactional
	public Customs customsSave(Customs customs) {
		return customsRepository.save(customs);
	}

	public Optional<Customs> customsFindById(int id) {
		return customsRepository.findById(id);
	}

	@Transactional
	public void customsDeleteById(int id) {
		customsRepository.deleteById(id);
	}

	public List<Customs> findCustomsByEmail(String email) {
		return customsRepository.findCustomRawByEmail(email);
	}

	public List<Customs> findCustomByEmailAndProductItems(String email, Integer productItemIds) {
		return customsRepository.findCustomByEmailAndProductItems(email, productItemIds);
	}

	@Transactional
	public void saveCustomFromCheckout(List<CheckoutCustomDTO> customs, int orderId) {
		for (CheckoutCustomDTO custom : customs) {
			Customs entity = customsRepository.findById(custom.getCustomId()).get();
			Customs customToSave = new Customs();
			Orders order = new Orders();
			order.setId(orderId);
			customToSave.setOrder(order);
			customToSave.setAccount(entity.getAccount());
			customToSave.setQty(custom.getQty());
			customToSave.setCanvasJson(entity.getCanvasJson());
			customToSave.setImageUrl(entity.getImageUrl());
			customToSave.setDesignName(entity.getDesignName());
			customToSave.setProductItems(entity.getProductItems());
			customsRepository.save(customToSave);
		}
	}
}