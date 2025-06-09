package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ProductImagesService {
	@Autowired
    private ProductImagesRepository productImagesRepository;

    public List<ProductImages> productImagesFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return productImagesRepository.findAll(pageable).getContent();
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