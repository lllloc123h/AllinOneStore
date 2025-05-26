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
    private ProductImagesRepository productImagesRepository;

    public List<ProductImages> productImagesFindAll() {
        return productImagesRepository.findAll();
    }
    public ProductImages productImagesSave(ProductImages productImages) {
        return productImagesRepository.save(productImages);
    }
    public Optional<ProductImages> productImagesFindById(int id) {
        return productImagesRepository.findById(id);
    }
    public void productImagesDeleteById(int id) {
        productImagesRepository.deleteById(id);
    }
}