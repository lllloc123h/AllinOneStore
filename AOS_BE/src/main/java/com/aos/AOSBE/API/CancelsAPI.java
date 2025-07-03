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

import com.aos.AOSBE.DTOS.CancelsDTOS;
import com.aos.AOSBE.Entity.Cancels;
import com.aos.AOSBE.Mapper.CancelsMapper;
import com.aos.AOSBE.Service.CancelsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class CancelsAPI {
	@Autowired
	private CancelsService cancelsService;

	@Autowired
	private CancelsMapper cancelsMapper;

	@GetMapping("/admin/Cancels")
	public ResponseEntity<?> getAllCancelsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<Cancels> pageResult = cancelsService.cancelsFindAll(page, size, filters);
		List<CancelsDTOS> cancels = pageResult.getContent().stream().map(cancelsMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", cancels);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);

	}

	@GetMapping("/admin/Cancels/{id}")
	public ResponseEntity<Cancels> getCancelsByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		Cancels cancels = (Cancels) cancelsService.cancelsFindById(id).orElse(new Cancels());
		return ResponseEntity.ok(cancels);
	}

	@PostMapping("/admin/Cancels")
	public ResponseEntity<Cancels> addNewCancels(@RequestBody CancelsDTOS entity) {

		Cancels saved = cancelsService.cancelsSave(cancelsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/Cancels/{id}")
	public ResponseEntity<?> updateCancels(@PathVariable int id, @RequestBody CancelsDTOS entity) {
		try {
			Cancels isExist = cancelsService.cancelsFindById(id).orElse(null);
			if (isExist != null) {
				Cancels update = cancelsMapper.mapperToObject(entity);
				cancelsService.cancelsSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@DeleteMapping("/admin/Cancels/{id}")
	public ResponseEntity<Void> deleteCancels(@PathVariable int id) {
		cancelsService.cancelsDeleteById(id);
		return ResponseEntity.noContent().build();
	}

}