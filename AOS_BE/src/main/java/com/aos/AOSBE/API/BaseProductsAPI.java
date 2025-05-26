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

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/Api/Admin")
@CrossOrigin(origins = "http://localhost:5173")
public class BaseProductsAPI {
	@Autowired
	private BaseProductsService baseProductsService;

	@GetMapping("/BaseProducts")
	public ResponseEntity<List<BaseProducts>> getAllBaseProductsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		List<BaseProducts> baseProducts = baseProductsService.baseProductsFindAll();
		return ResponseEntity.ok(baseProducts);
	}

	@GetMapping("/BaseProducts/{id}")
	public ResponseEntity<BaseProducts> getBaseProductsByIdApi(@PathVariable int id) {
		BaseProducts baseProducts =(BaseProducts)baseProductsService.baseProductsFindById(id).orElse(new BaseProducts());
		return ResponseEntity.ok(baseProducts);
	}
	@PostMapping("/BaseProducts")
	public ResponseEntity<BaseProducts> addNewBaseProducts(@RequestBody BaseProducts entity) {
	    BaseProducts saved = baseProductsService.baseProductsSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/BaseProducts")
	public ResponseEntity<BaseProducts> updateBaseProducts(@RequestBody BaseProducts entity) {
	    BaseProducts updated = baseProductsService.baseProductsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/BaseProducts/{id}")
	public ResponseEntity<Void> deleteBaseProducts(@PathVariable int id) {
	    baseProductsService.baseProductsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
