package com.aos.AOSBE.API;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
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
public class PromotionsAPI {
	@Autowired
	private PromotionsService promotionsService;
	
	@Autowired
	private PromotionsMapper promotionsMapper;

	@GetMapping("/admin/Promotions")
	public ResponseEntity<List<PromotionsDTOS>> getAllPromotionsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<PromotionsDTOS> promotions = new ArrayList<PromotionsDTOS>();
		promotionsService.promotionsFindAll(page, size).forEach(e -> {
			promotions.add(promotionsMapper.mapper(e));
		});
		return ResponseEntity.ok(promotions);
	}

	@GetMapping("/admin/Promotions/{id}")
	public ResponseEntity<Promotions> getPromotionsByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		Promotions promotions =(Promotions)promotionsService.promotionsFindById(id).orElse(new Promotions());
		return ResponseEntity.ok(promotions);
	}
	@PostMapping("/admin/Promotions")
	public ResponseEntity<Promotions> addNewPromotions(@RequestBody PromotionsDTOS entity) {
	    
	    Promotions saved = promotionsService.promotionsSave(promotionsMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/Promotions/{id}")
	public ResponseEntity<?> updatePromotions( @PathVariable int id,@RequestBody PromotionsDTOS entity) {
			try {
			Promotions  isExist = promotionsService.promotionsFindById(id).orElse(null);
			if (isExist != null) {
				Promotions  update = promotionsMapper.mapperToObject(entity);
				promotionsService.promotionsSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		} 
	}
	@DeleteMapping("/admin/Promotions/{id}")
	public ResponseEntity<Void> deletePromotions(@PathVariable int id) {
	    promotionsService.promotionsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
