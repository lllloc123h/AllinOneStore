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

import com.aos.AOSBE.DTOS.CostHistoriesDTOS;
import com.aos.AOSBE.Entity.CostHistories;
import com.aos.AOSBE.Mapper.CostHistoriesMapper;
import com.aos.AOSBE.Service.CostHistoriesService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class CostHistoriesAPI {
	@Autowired
	private CostHistoriesService costHistoriesService;

	@Autowired
	private CostHistoriesMapper costHistoriesMapper;

	@GetMapping("/admin/CostHistories")
	public ResponseEntity<?> getAllCostHistoriesApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<CostHistories> pageResult = costHistoriesService.costHistoriesFindAll(page, size, filters);
		List<CostHistoriesDTOS> costHistories = pageResult.getContent().stream().map(costHistoriesMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", costHistories);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/admin/CostHistories/{id}")
	public ResponseEntity<CostHistories> getCostHistoriesByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		CostHistories costHistories = (CostHistories) costHistoriesService.costHistoriesFindById(id)
				.orElse(new CostHistories());
		return ResponseEntity.ok(costHistories);
	}

	@PostMapping("/admin/CostHistories")
	public ResponseEntity<CostHistories> addNewCostHistories(@RequestBody CostHistoriesDTOS entity) {

		CostHistories saved = costHistoriesService.costHistoriesSave(costHistoriesMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/CostHistories/{id}")
	public ResponseEntity<?> updateCostHistories(@PathVariable int id, @RequestBody CostHistoriesDTOS entity) {
		try {
			CostHistories isExist = costHistoriesService.costHistoriesFindById(id).orElse(null);
			if (isExist != null) {
				CostHistories update = costHistoriesMapper.mapperToObject(entity);
				costHistoriesService.costHistoriesSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@DeleteMapping("/admin/CostHistories/{id}")
	public ResponseEntity<Void> deleteCostHistories(@PathVariable int id) {
		costHistoriesService.costHistoriesDeleteById(id);
		return ResponseEntity.noContent().build();
	}

}