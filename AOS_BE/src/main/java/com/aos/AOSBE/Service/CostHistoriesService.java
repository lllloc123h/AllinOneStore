package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.CostHistories;
import com.aos.AOSBE.Repository.CostHistoriesRepository;

@Service
public class CostHistoriesService {
	@Autowired
	private CostHistoriesRepository costHistoriesRepository;

	public List<CostHistories> costHistoriesFindAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return costHistoriesRepository.findAll(pageable).getContent();
	}

	@Transactional
	public CostHistories costHistoriesSave(CostHistories costHistories) {
		return costHistoriesRepository.save(costHistories);
	}

	public Optional<CostHistories> costHistoriesFindById(int id) {
		return costHistoriesRepository.findById(id);
	}

	@Transactional
	public void costHistoriesDeleteById(int id) {
		costHistoriesRepository.deleteById(id);
	}
}