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
@Service
public class ProductItemsService {
	@Autowired
    private ProductItemsRepository productItemsRepository;

    public List<ProductItems> productItemsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return productItemsRepository.findAll(pageable).getContent();
    }
    public ProductItems productItemsSave(ProductItems productItems) {
        return productItemsRepository.save(productItems);
    }
    public Optional<ProductItems> productItemsFindById(int id) {
        return productItemsRepository.findById(id);
    }
    public void productItemsDeleteById(int id) {
        productItemsRepository.deleteById(id);
    }
}