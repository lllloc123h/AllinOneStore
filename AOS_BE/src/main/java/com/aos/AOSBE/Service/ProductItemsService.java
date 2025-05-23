package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class ProductItemsService {
	@Autowired
    private ProductItemsRepository productProductItemsRepository;

    public List<ProductItems> productProductItemsFindAll() {
        return productProductItemsRepository.findAll();
    }
    public ProductItems productProductItemsSave(ProductItems productProductItems) {
        return productProductItemsRepository.save(productProductItems);
    }
    public Optional<ProductItems> productProductItemsFindById(int id) {
        return productProductItemsRepository.findById(id);
    }
    public void productProductItemsDeleteById(int id) {
        productProductItemsRepository.deleteById(id);
    }
}