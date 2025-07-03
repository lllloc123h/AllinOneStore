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

import com.aos.AOSBE.Entity.ProductImages;
import com.aos.AOSBE.Repository.ProductImagesRepository;

@Service
public class ProductImagesService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private ProductImagesRepository productImagesRepository;

	public Page<ProductImages> productImagesFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<ProductImages> spec = specBuilder.buildFilter(filters);
		return productImagesRepository.findAll(spec, pageable);
	}

	@Transactional
	public ProductImages productImagesSave(ProductImages productImages) {
		return productImagesRepository.save(productImages);
	}

	public Optional<ProductImages> productImagesFindById(int id) {
		return productImagesRepository.findById(id);
	}

	@Transactional
	public void productImagesDeleteById(int id) {
		productImagesRepository.deleteById(id);
	}
}