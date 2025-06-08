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
public class PriceHistoriesService {
	@Autowired
    private PriceHistoriesRepository priceHistoriesRepository;

    public List<PriceHistories> priceHistoriesFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return priceHistoriesRepository.findAll(pageable).getContent();
    }
    @Transactional
    public PriceHistories priceHistoriesSave(PriceHistories priceHistories) {
        return priceHistoriesRepository.save(priceHistories);
    }
    public Optional<PriceHistories> priceHistoriesFindById(int id) {
        return priceHistoriesRepository.findById(id);
    }
    @Transactional
    public void priceHistoriesDeleteById(int id) {
        priceHistoriesRepository.deleteById(id);
    }
}