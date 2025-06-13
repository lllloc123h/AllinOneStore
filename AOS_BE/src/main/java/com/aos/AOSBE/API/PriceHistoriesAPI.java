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
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class PriceHistoriesAPI {
	@Autowired
	private PriceHistoriesService priceHistoriesService;
	
	@Autowired
	private PriceHistoriesMapper priceHistoriesMapper;

	@GetMapping("/admin/PriceHistories")
	public ResponseEntity<List<PriceHistoriesDTOS>> getAllPriceHistoriesApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<PriceHistoriesDTOS> priceHistories = new ArrayList<PriceHistoriesDTOS>();
		priceHistoriesService.priceHistoriesFindAll(page, size).forEach(e -> {
			priceHistories.add(priceHistoriesMapper.mapper(e));
		});
		return ResponseEntity.ok(priceHistories);
	}

	@GetMapping("/admin/PriceHistories/{id}")
	public ResponseEntity<PriceHistories> getPriceHistoriesByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		PriceHistories priceHistories =(PriceHistories)priceHistoriesService.priceHistoriesFindById(id).orElse(new PriceHistories());
		return ResponseEntity.ok(priceHistories);
	}
	@PostMapping("/admin/PriceHistories")
	public ResponseEntity<PriceHistories> addNewPriceHistories(@RequestBody PriceHistoriesDTOS entity) {
	    
	    PriceHistories saved = priceHistoriesService.priceHistoriesSave(priceHistoriesMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/PriceHistories")
	public ResponseEntity<?> updatePriceHistories(@RequestBody PriceHistories entity) {
			try {
			PriceHistories  isExist = priceHistoriesService.priceHistoriesFindById(id).orElse(null);
			if (isExist != null) {
				PriceHistories  update = priceHistoriesMapper.mapperToObject(entity);
				priceHistoriesService.priceHistoriesSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		} 
	}
	@DeleteMapping("/admin/PriceHistories/{id}")
	public ResponseEntity<Void> deletePriceHistories(@PathVariable int id) {
	    priceHistoriesService.priceHistoriesDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
