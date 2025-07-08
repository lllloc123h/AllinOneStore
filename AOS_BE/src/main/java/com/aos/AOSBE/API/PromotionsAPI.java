package com.aos.AOSBE.API;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aos.AOSBE.DTOS.PromotionsDTOS;
import com.aos.AOSBE.Entity.Promotions;
import com.aos.AOSBE.Mapper.PromotionsMapper;
import com.aos.AOSBE.Service.PromotionsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class PromotionsAPI {
	@Autowired
	private PromotionsService promotionsService;

	@Autowired
	private PromotionsMapper promotionsMapper;

	@GetMapping("/Promotions/{productItemId}")
	public ResponseEntity<?> getAllPromotionByProductItemId(@PathVariable("productItemId") int productItemId) {
		List<Promotions> promotions = promotionsService.promotionsFindByIsActiveTrue(productItemId);
		List<PromotionsDTOS> promotionsDTOS = promotions.stream().map(promotionsMapper :: mapper).toList();
		return ResponseEntity.ok(promotionsDTOS);
	}


	@GetMapping("/admin/Promotions")
	public ResponseEntity<?> getAllPromotionsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<Promotions> pageResult = promotionsService.promotionsFindAll(page, size, filters);
		List<PromotionsDTOS> promotions = pageResult.getContent().stream().map(promotionsMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", promotions);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);

	}

	@GetMapping("/admin/Promotions/{id}")
	public ResponseEntity<Promotions> getPromotionsByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		Promotions promotions = (Promotions) promotionsService.promotionsFindById(id).orElse(new Promotions());
		return ResponseEntity.ok(promotions);
	}

	@PostMapping("/admin/Promotions")
	public ResponseEntity<Promotions> addNewPromotions(@RequestBody PromotionsDTOS entity) {

		Promotions saved = promotionsService.promotionsSave(promotionsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/Promotions/{id}")
	public ResponseEntity<?> updatePromotions(@PathVariable int id, @RequestBody PromotionsDTOS entity) {
		try {
			Promotions isExist = promotionsService.promotionsFindById(id).orElse(null);
			if (isExist != null) {
				Promotions update = promotionsMapper.mapperToObject(entity);
				promotionsService.promotionsSave(update);
				return ResponseEntity.ok().body(Map.of("measage", "Update successfuly", "update", update));
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