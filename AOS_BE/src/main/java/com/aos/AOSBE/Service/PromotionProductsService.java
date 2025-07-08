package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.PromotionProducts;
import com.aos.AOSBE.Repository.PromotionProductsRepository;

@Service
public class PromotionProductsService {

    @Autowired
    private PromotionProductsRepository promotionProductsRepository;

    public Optional<PromotionProducts> findById(int id) {
        return promotionProductsRepository.findById(id);
    }

    @Transactional
    public PromotionProducts save(PromotionProducts entity) {
        return promotionProductsRepository.save(entity);
    }

    @Transactional
    public void deleteById(int id) {
        promotionProductsRepository.deleteById(id);
    }

    public List<PromotionProducts> findByProductItemsId(int productItemId) {
        return promotionProductsRepository.findByProductItems_Id(productItemId);
    }
    public List<PromotionProducts> findPromotionProductsByPromotionId(int promotionId) {
        return promotionProductsRepository.findPromotionProductsByPromotionId(promotionId);
    }

}
