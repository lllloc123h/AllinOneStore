package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class WorkHistoriesService {
	@Autowired
    private WorkHistoriesRepository workWorkHistoriesRepository;

    public List<WorkHistories> workWorkHistoriesFindAll() {
        return workWorkHistoriesRepository.findAll();
    }
    public WorkHistories workWorkHistoriesSave(WorkHistories workWorkHistories) {
        return workWorkHistoriesRepository.save(workWorkHistories);
    }
    public Optional<WorkHistories> workWorkHistoriesFindById(int id) {
        return workWorkHistoriesRepository.findById(id);
    }
    public void workWorkHistoriesDeleteById(int id) {
        workWorkHistoriesRepository.deleteById(id);
    }
}