package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class PriceHistoriesService {
	@Autowired
    private PriceHistoriesRepository pricePriceHistoriesRepository;

    public List<PriceHistories> pricePriceHistoriesFindAll() {
        return pricePriceHistoriesRepository.findAll();
    }
    public PriceHistories pricePriceHistoriesSave(PriceHistories pricePriceHistories) {
        return pricePriceHistoriesRepository.save(pricePriceHistories);
    }
    public Optional<PriceHistories> pricePriceHistoriesFindById(int id) {
        return pricePriceHistoriesRepository.findById(id);
    }
    public void pricePriceHistoriesDeleteById(int id) {
        pricePriceHistoriesRepository.deleteById(id);
    }
}