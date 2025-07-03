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

import com.aos.AOSBE.DTOS.PriceHistoriesDTOS;
import com.aos.AOSBE.Entity.PriceHistories;
import com.aos.AOSBE.Mapper.PriceHistoriesMapper;
import com.aos.AOSBE.Service.PriceHistoriesService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class PriceHistoriesAPI {
	@Autowired
	private PriceHistoriesService priceHistoriesService;

	@Autowired
	private PriceHistoriesMapper priceHistoriesMapper;

	@GetMapping("/admin/PriceHistories")
	public ResponseEntity<?> getAllPriceHistoriesApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<PriceHistories> pageResult = priceHistoriesService.priceHistoriesFindAll(page, size, filters);
		List<PriceHistoriesDTOS> priceHistories = pageResult.getContent().stream().map(priceHistoriesMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", priceHistories);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);

	}

	@GetMapping("/admin/PriceHistories/{id}")
	public ResponseEntity<PriceHistories> getPriceHistoriesByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		PriceHistories priceHistories = (PriceHistories) priceHistoriesService.priceHistoriesFindById(id)
				.orElse(new PriceHistories());
		return ResponseEntity.ok(priceHistories);
	}

	@PostMapping("/admin/PriceHistories")
	public ResponseEntity<PriceHistories> addNewPriceHistories(@RequestBody PriceHistoriesDTOS entity) {

		PriceHistories saved = priceHistoriesService.priceHistoriesSave(priceHistoriesMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/PriceHistories/{id}")
	public ResponseEntity<?> updatePriceHistories(@PathVariable int id, @RequestBody PriceHistoriesDTOS entity) {
		try {
			PriceHistories isExist = priceHistoriesService.priceHistoriesFindById(id).orElse(null);
			if (isExist != null) {
				PriceHistories update = priceHistoriesMapper.mapperToObject(entity);
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