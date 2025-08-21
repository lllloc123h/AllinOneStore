package com.aos.AOSBE.API;

import java.util.ArrayList;
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

import com.aos.AOSBE.DTOS.VariantValuesDTOS;
import com.aos.AOSBE.Entity.VariantValues;
import com.aos.AOSBE.Mapper.VariantValuesMapper;
import com.aos.AOSBE.Service.VariantValuesService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class VariantValuesAPI {
	@Autowired
	private VariantValuesService variantValuesService;

	@Autowired
	private VariantValuesMapper variantValuesMapper;

	@GetMapping("/admin/VariantValues")
	public ResponseEntity<?> getAllVariantValuesApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<VariantValues> pageResult = variantValuesService.variantValuesFindAll(page, size, filters);
		List<VariantValuesDTOS> variantValues = pageResult.getContent().stream().map(variantValuesMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", variantValues);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/admin/VariantValues/{id}")
	public ResponseEntity<VariantValues> getVariantValuesByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		VariantValues variantValues = (VariantValues) variantValuesService.variantValuesFindById(id)
				.orElse(new VariantValues());
		return ResponseEntity.ok(variantValues);
	}

	@PostMapping("/admin/VariantValues")
	public ResponseEntity<?> addNewVariantValues(@RequestBody VariantValuesDTOS entity) {
		try {
			VariantValues mapped = variantValuesMapper.mapperToObject(entity);
			VariantValues saved = variantValuesService.variantValuesSave(mapped);
			return ResponseEntity.ok(saved);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra: " + e.getMessage()));
		}
	}

	@PutMapping("/admin/VariantValues/{id}")
	public ResponseEntity<?> updateVariantValues(@PathVariable int id, @RequestBody VariantValuesDTOS entity) {
		try {
			VariantValues mapped = variantValuesMapper.mapperToObject(entity);
			VariantValues updated = variantValuesService.variantValuesSave(mapped);
			return ResponseEntity.ok(updated);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra: " + e.getMessage()));
		}

	}

	@DeleteMapping("/admin/VariantValues/{id}")
	public ResponseEntity<Void> deleteVariantValues(@PathVariable int id) {
		variantValuesService.variantValuesDeleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/VariantValues")
	public ResponseEntity<?> getAllVariantValuesApiToQuery(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "100") int size,
			@RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Map<String, List<VariantValuesDTOS>> variantValuesForList = new HashMap<>();

		List<VariantValuesDTOS> variantValues = new ArrayList<VariantValuesDTOS>();
		variantValuesService.variantValuesFindAll(page, size, filters).forEach(e -> {
			variantValues.add(variantValuesMapper.mapper(e));
		});
		variantValuesForList = variantValues.stream().collect(Collectors.groupingBy(VariantValuesDTOS::getName));
		return ResponseEntity.ok(variantValuesForList);
	}
	@GetMapping("/VariantValues/values")
	public  ResponseEntity<?> getAllByVariantsName(@RequestParam("name") String variantsName) {
		List<VariantValues> variantValues = variantValuesService.findByVariantsName(variantsName);
		List<VariantValuesDTOS> variantValuesDTOS = variantValues.stream()
				.map(variantValuesMapper::mapper).collect(Collectors.toList());
		return ResponseEntity.ok(variantValuesDTOS);
	}

}