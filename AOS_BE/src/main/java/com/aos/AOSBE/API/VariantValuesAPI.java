package com.aos.AOSBE.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseEntity<List<VariantValuesDTOS>> getAllVariantValuesApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {

		List<VariantValuesDTOS> variantValues = new ArrayList<VariantValuesDTOS>();
		variantValuesService.variantValuesFindAll(page, size).forEach(e -> {
			variantValues.add(variantValuesMapper.mapper(e));
		});
		return ResponseEntity.ok(variantValues);
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
	public ResponseEntity<VariantValues> addNewVariantValues(@RequestBody VariantValuesDTOS entity) {

		VariantValues saved = variantValuesService.variantValuesSave(variantValuesMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/VariantValues")
	public ResponseEntity<VariantValues> updateVariantValues(@RequestBody VariantValues entity) {
		VariantValues updated = variantValuesService.variantValuesSave(entity);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/admin/VariantValues/{id}")
	public ResponseEntity<Void> deleteVariantValues(@PathVariable int id) {
		variantValuesService.variantValuesDeleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/VariantValues")
	public ResponseEntity<?> getAllVariantValuesApiToQuery(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "100") int size) {

		Map<String, List<VariantValuesDTOS>> variantValuesForList = new HashMap<>();

		List<VariantValuesDTOS> variantValues = new ArrayList<VariantValuesDTOS>();
		variantValuesService.variantValuesFindAll(page, size).forEach(e -> {
			variantValues.add(variantValuesMapper.mapper(e));
		});
		variantValuesForList = variantValues.stream().collect(Collectors.groupingBy(VariantValuesDTOS::getName));
		return ResponseEntity.ok(variantValuesForList);
	}

}
