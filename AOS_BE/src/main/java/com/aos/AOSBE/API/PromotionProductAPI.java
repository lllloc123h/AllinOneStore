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
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class PromotionProductAPI {
	@Autowired
	private PromotionProductService promotionProductService;
	private PromotionProductMapper promotionProductMapper=new PromotionProductMapper();

	@GetMapping("/PromotionProduct")
	public ResponseEntity<List<PromotionProductDTOS>> getAllPromotionProductApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<PromotionProductDTOS> promotionProduct = new ArrayList<PromotionProductDTOS>();
		promotionProductService.promotionProductFindAll(page, size).forEach(e -> {
			promotionProduct.add(promotionProductMapper.mapper(e));
		});
		return ResponseEntity.ok(promotionProduct);
	}

	@GetMapping("/PromotionProduct/{id}")
	public ResponseEntity<PromotionProduct> getPromotionProductByIdApi(@PathVariable int id) {
		PromotionProduct promotionProduct =(PromotionProduct)promotionProductService.promotionProductFindById(id).orElse(new PromotionProduct());
		return ResponseEntity.ok(promotionProduct);
	}
	@PostMapping("/PromotionProduct")
	public ResponseEntity<PromotionProduct> addNewPromotionProduct(@RequestBody PromotionProductDTOS entity) {
	    
	    PromotionProduct saved = promotionProductService.promotionProductSave(promotionProductMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/PromotionProduct")
	public ResponseEntity<PromotionProduct> updatePromotionProduct(@RequestBody PromotionProduct entity) {
	    PromotionProduct updated = promotionProductService.promotionProductSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/PromotionProduct/{id}")
	public ResponseEntity<Void> deletePromotionProduct(@PathVariable int id) {
	    promotionProductService.promotionProductDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
