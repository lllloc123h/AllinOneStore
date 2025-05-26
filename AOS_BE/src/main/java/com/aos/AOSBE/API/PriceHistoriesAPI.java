package com.aos.AOSBE.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/Api/Admin")
@CrossOrigin(origins = "http://localhost:5173")
public class PriceHistoriesAPI {
	@Autowired
	private PriceHistoriesService priceHistoriesService;

	@GetMapping("/PriceHistories")
	public ResponseEntity<List<PriceHistories>> getAllPriceHistoriesApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		List<PriceHistories> priceHistories = priceHistoriesService.priceHistoriesFindAll();
		return ResponseEntity.ok(priceHistories);
	}

	@GetMapping("/PriceHistories/{id}")
	public ResponseEntity<PriceHistories> getPriceHistoriesByIdApi(@PathVariable int id) {
		PriceHistories priceHistories =(PriceHistories)priceHistoriesService.priceHistoriesFindById(id).orElse(new PriceHistories());
		return ResponseEntity.ok(priceHistories);
	}
	@PostMapping("/PriceHistories")
	public ResponseEntity<PriceHistories> addNewPriceHistories(@RequestBody PriceHistories entity) {
	    PriceHistories saved = priceHistoriesService.priceHistoriesSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/PriceHistories")
	public ResponseEntity<PriceHistories> updatePriceHistories(@RequestBody PriceHistories entity) {
	    PriceHistories updated = priceHistoriesService.priceHistoriesSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/PriceHistories/{id}")
	public ResponseEntity<Void> deletePriceHistories(@PathVariable int id) {
	    priceHistoriesService.priceHistoriesDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
