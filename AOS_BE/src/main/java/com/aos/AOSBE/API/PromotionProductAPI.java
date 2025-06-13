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
public class PromotionProductAPI {
	@Autowired
	private PromotionProductService promotionProductService;
	
	@Autowired
	private PromotionProductMapper promotionProductMapper;

	@GetMapping("/admin/PromotionProduct")
	public ResponseEntity<List<PromotionProductDTOS>> getAllPromotionProductApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<PromotionProductDTOS> promotionProduct = new ArrayList<PromotionProductDTOS>();
		promotionProductService.promotionProductFindAll(page, size).forEach(e -> {
			promotionProduct.add(promotionProductMapper.mapper(e));
		});
		return ResponseEntity.ok(promotionProduct);
	}

	@GetMapping("/admin/PromotionProduct/{id}")
	public ResponseEntity<PromotionProduct> getPromotionProductByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		PromotionProduct promotionProduct =(PromotionProduct)promotionProductService.promotionProductFindById(id).orElse(new PromotionProduct());
		return ResponseEntity.ok(promotionProduct);
	}
	@PostMapping("/admin/PromotionProduct")
	public ResponseEntity<PromotionProduct> addNewPromotionProduct(@RequestBody PromotionProductDTOS entity) {
	    
	    PromotionProduct saved = promotionProductService.promotionProductSave(promotionProductMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/PromotionProduct")
	public ResponseEntity<?> updatePromotionProduct(@RequestBody PromotionProduct entity) {
			try {
			PromotionProduct  isExist = promotionProductService.promotionProductFindById(id).orElse(null);
			if (isExist != null) {
				PromotionProduct  update = promotionProductMapper.mapperToObject(entity);
				promotionProductService.promotionProductSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		} 
	}
	@DeleteMapping("/admin/PromotionProduct/{id}")
	public ResponseEntity<Void> deletePromotionProduct(@PathVariable int id) {
	    promotionProductService.promotionProductDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
