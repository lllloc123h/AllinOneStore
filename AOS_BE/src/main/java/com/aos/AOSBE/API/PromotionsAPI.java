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
public class PromotionsAPI {
	@Autowired
	private PromotionsService promotionsService;

	@GetMapping("/Promotions")
	public ResponseEntity<List<Promotions>> getAllPromotionsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		List<Promotions> promotions = promotionsService.promotionsFindAll();
		return ResponseEntity.ok(promotions);
	}

	@GetMapping("/Promotions/{id}")
	public ResponseEntity<Promotions> getPromotionsByIdApi(@PathVariable int id) {
		Promotions promotions =(Promotions)promotionsService.promotionsFindById(id).orElse(new Promotions());
		return ResponseEntity.ok(promotions);
	}
	@PostMapping("/Promotions")
	public ResponseEntity<Promotions> addNewPromotions(@RequestBody Promotions entity) {
	    Promotions saved = promotionsService.promotionsSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Promotions")
	public ResponseEntity<Promotions> updatePromotions(@RequestBody Promotions entity) {
	    Promotions updated = promotionsService.promotionsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Promotions/{id}")
	public ResponseEntity<Void> deletePromotions(@PathVariable int id) {
	    promotionsService.promotionsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
