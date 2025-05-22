package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class PromotionProductService {
	@Autowired
    private PromotionProductRepository promotionProductRepository;

    public List<PromotionProduct> promotionProductFindAll() {
        return promotionProductRepository.findAll();
    }
    public PromotionProduct promotionProductSave(PromotionProduct promotionProduct) {
        return promotionProductRepository.save(promotionProduct);
    }
    public Optional<PromotionProduct> promotionProductFindById(int id) {
        return promotionProductRepository.findById(id);
    }
    public void promotionProductDeleteById(int id) {
        promotionProductRepository.deleteById(id);
    }
}