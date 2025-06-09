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
public class PromotionsService {
	@Autowired
    private PromotionsRepository promotionsRepository;

    public List<Promotions> promotionsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return promotionsRepository.findAll(pageable).getContent();
    }
    @Transactional
    public Promotions promotionsSave(Promotions promotions) {
        return promotionsRepository.save(promotions);
    }
    public Optional<Promotions> promotionsFindById(int id) {
        return promotionsRepository.findById(id);
    }
    @Transactional
    public void promotionsDeleteById(int id) {
        promotionsRepository.deleteById(id);
    }
}