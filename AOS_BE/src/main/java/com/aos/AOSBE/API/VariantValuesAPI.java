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
@RequestMapping("/Api/Admin")
@CrossOrigin(origins = "http://localhost:5173")
public class VariantValuesAPI {
	@Autowired
	private VariantValuesService variantValuesService;
	private VariantValuesMapper variantValuesMapper=new VariantValuesMapper();

	@GetMapping("/VariantValues")
	public ResponseEntity<List<VariantValuesDTOS>> getAllVariantValuesApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<VariantValuesDTOS> variantValues = new ArrayList<VariantValuesDTOS>();
		variantValuesService.variantValuesFindAll(page, size).forEach(e -> {
			variantValues.add(variantValuesMapper.mapper(e));
		});
		return ResponseEntity.ok(variantValues);
	}

	@GetMapping("/VariantValues/{id}")
	public ResponseEntity<VariantValues> getVariantValuesByIdApi(@PathVariable int id) {
		VariantValues variantValues =(VariantValues)variantValuesService.variantValuesFindById(id).orElse(new VariantValues());
		return ResponseEntity.ok(variantValues);
	}
	@PostMapping("/VariantValues")
	public ResponseEntity<VariantValues> addNewVariantValues(@RequestBody VariantValuesDTOS entity) {
	    
	    VariantValues saved = variantValuesService.variantValuesSave(variantValuesMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/VariantValues")
	public ResponseEntity<VariantValues> updateVariantValues(@RequestBody VariantValues entity) {
	    VariantValues updated = variantValuesService.variantValuesSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/VariantValues/{id}")
	public ResponseEntity<Void> deleteVariantValues(@PathVariable int id) {
	    variantValuesService.variantValuesDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
