package com.aos.AOSBE.API;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Mapper.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/Api/Admin")
@CrossOrigin(origins = "http://localhost:5173")
public class CostHistoriesAPI {
	@Autowired
	private CostHistoriesService costHistoriesService;
	private CostHistoriesMapper costHistoriesMapper=new CostHistoriesMapper();

	@GetMapping("/CostHistories")
	public ResponseEntity<List<CostHistoriesDTOS>> getAllCostHistoriesApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<CostHistoriesDTOS> costHistories = new ArrayList<CostHistoriesDTOS>();
		costHistoriesService.costHistoriesFindAll(page, size).forEach(e -> {
			costHistories.add(costHistoriesMapper.mapper(e));
		});
		return ResponseEntity.ok(costHistories);
	}

	@GetMapping("/CostHistories/{id}")
	public ResponseEntity<CostHistories> getCostHistoriesByIdApi(@PathVariable int id) {
		CostHistories costHistories =(CostHistories)costHistoriesService.costHistoriesFindById(id).orElse(new CostHistories());
		return ResponseEntity.ok(costHistories);
	}
	@PostMapping("/CostHistories")
	public ResponseEntity<CostHistories> addNewCostHistories(@RequestBody CostHistoriesDTOS entity) {
	    
	    CostHistories saved = costHistoriesService.costHistoriesSave(costHistoriesMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/CostHistories")
	public ResponseEntity<CostHistories> updateCostHistories(@RequestBody CostHistories entity) {
	    CostHistories updated = costHistoriesService.costHistoriesSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/CostHistories/{id}")
	public ResponseEntity<Void> deleteCostHistories(@PathVariable int id) {
	    costHistoriesService.costHistoriesDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
