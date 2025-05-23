package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class PromotionsService {
	@Autowired
    private PromotionsRepository promotionsRepository;

    public List<Promotions> promotionsFindAll() {
        return promotionsRepository.findAll();
    }
    public Promotions promotionsSave(Promotions promotions) {
        return promotionsRepository.save(promotions);
    }
    public Optional<Promotions> promotionsFindById(int id) {
        return promotionsRepository.findById(id);
    }
    public void promotionsDeleteById(int id) {
        promotionsRepository.deleteById(id);
    }
}