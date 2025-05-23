package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class CostHistoriesService {
	@Autowired
    private CostHistoriesRepository costCostHistoriesRepository;

    public List<CostHistories> costCostHistoriesFindAll() {
        return costCostHistoriesRepository.findAll();
    }
    public CostHistories costCostHistoriesSave(CostHistories costCostHistories) {
        return costCostHistoriesRepository.save(costCostHistories);
    }
    public Optional<CostHistories> costCostHistoriesFindById(int id) {
        return costCostHistoriesRepository.findById(id);
    }
    public void costCostHistoriesDeleteById(int id) {
        costCostHistoriesRepository.deleteById(id);
    }
}