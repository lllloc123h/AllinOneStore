package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class ProductImagesService {
	@Autowired
    private ProductImagesRepository productProductImagesRepository;

    public List<ProductImages> productProductImagesFindAll() {
        return productProductImagesRepository.findAll();
    }
    public ProductImages productProductImagesSave(ProductImages productProductImages) {
        return productProductImagesRepository.save(productProductImages);
    }
    public Optional<ProductImages> productProductImagesFindById(int id) {
        return productProductImagesRepository.findById(id);
    }
    public void productProductImagesDeleteById(int id) {
        productProductImagesRepository.deleteById(id);
    }
}