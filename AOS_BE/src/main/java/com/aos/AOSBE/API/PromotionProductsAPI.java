package com.aos.AOSBE.API;

import com.aos.AOSBE.DTOS.PromotionProductsDTOS;
import com.aos.AOSBE.DTOS.PromotionsDTOS;
import com.aos.AOSBE.Entity.PromotionProducts;
import com.aos.AOSBE.Entity.Promotions;
import com.aos.AOSBE.Mapper.PromotionProductsMapper;
import com.aos.AOSBE.Service.PromotionProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

import com.aos.AOSBE.DTOS.PromotionProductsDTOS;
import com.aos.AOSBE.Entity.PromotionProducts;
import com.aos.AOSBE.Mapper.PromotionProductsMapper;
import com.aos.AOSBE.Service.PromotionProductsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class PromotionProductsAPI {
	@Autowired
	private PromotionProductsService promotionProductsService;
	@Autowired
	private PromotionProductsMapper promotionProductsMapper;

	@GetMapping("/admin/PromotionProducts")
	public ResponseEntity<?> getAllPromotionProductsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<PromotionProducts> pageResult = promotionProductsService.promotionsFindAll(page, size, filters);
		List<PromotionProductsDTOS> promotions = pageResult.getContent().stream().map(promotionProductsMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", promotions);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/admin/PromotionProducts/{id}")
	public ResponseEntity<?> getPromotionProductsById(@PathVariable int id) {
		PromotionProducts promotionProduct = promotionProductsService.findById(id).orElse(new PromotionProducts());
		return ResponseEntity.ok(promotionProductsMapper.mapper(promotionProduct));
	}
	@GetMapping("/admin/promotionproducts")
	public ResponseEntity<?> getPromotionProductsByPromotionId(@RequestParam("promotionId") int promotionId) {
		List<PromotionProducts> promotionProducts = promotionProductsService
				.findPromotionProductsByPromotionsId(promotionId);
		List<PromotionProductsDTOS> promotionProductsDTOS = promotionProducts.stream()
				.map(promotionProductsMapper::mapper).toList();
		return ResponseEntity.ok(promotionProductsDTOS);
	}

	@PostMapping("/admin/PromotionProducts")
	public ResponseEntity<?> addNewPromotions(@RequestBody PromotionProductsDTOS entity) {
		try {
			PromotionProducts saved = promotionProductsService.save(promotionProductsMapper.mapperToObject(entity));
			return ResponseEntity.ok(saved);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra"));
		}
	}

	@PutMapping("/admin/PromotionProducts/{id}")
	public ResponseEntity<?> updatePromotions(@PathVariable int id, @RequestBody PromotionProductsDTOS entity) {
		try {
			PromotionProducts isExist = promotionProductsService.findById(id).orElse(null);
			if (isExist != null) {
				PromotionProducts update = promotionProductsMapper.mapperToObject(entity);
				promotionProductsService.save(update);
				return ResponseEntity.ok().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@DeleteMapping("/admin/PromotionProducts/{id}")
	public ResponseEntity<Void> deletePromotions(@PathVariable int id) {
		promotionProductsService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}


