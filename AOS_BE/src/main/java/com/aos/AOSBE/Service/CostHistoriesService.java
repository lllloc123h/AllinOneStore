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
public class CostHistoriesService {
	@Autowired
    private CostHistoriesRepository costHistoriesRepository;

    public List<CostHistories> costHistoriesFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return costHistoriesRepository.findAll(pageable).getContent();
    }
    public CostHistories costHistoriesSave(CostHistories costHistories) {
        return costHistoriesRepository.save(costHistories);
    }
    public Optional<CostHistories> costHistoriesFindById(int id) {
        return costHistoriesRepository.findById(id);
    }
    public void costHistoriesDeleteById(int id) {
        costHistoriesRepository.deleteById(id);
    }
}