package com.aos.AOSBE.API;

import java.util.List;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/Api/Admin")
@CrossOrigin(origins = "http://localhost:5173")
public class VariantsAPI {
	@Autowired
	private VariantsService variantsService;

	@GetMapping("/Variants")
	public ResponseEntity<List<Variants>> getAllVariantsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<Variants> variants = variantsService.variantsFindAll(page,size);
		return ResponseEntity.ok(variants);
	}

	@GetMapping("/Variants/{id}")
	public ResponseEntity<Variants> getVariantsByIdApi(@PathVariable int id) {
		Variants variants =(Variants)variantsService.variantsFindById(id).orElse(new Variants());
		return ResponseEntity.ok(variants);
	}
	@PostMapping("/Variants")
	public ResponseEntity<Variants> addNewVariants(@RequestBody Variants entity) {
	    Variants saved = variantsService.variantsSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Variants")
	public ResponseEntity<Variants> updateVariants(@RequestBody Variants entity) {
	    Variants updated = variantsService.variantsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Variants/{id}")
	public ResponseEntity<Void> deleteVariants(@PathVariable int id) {
	    variantsService.variantsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
