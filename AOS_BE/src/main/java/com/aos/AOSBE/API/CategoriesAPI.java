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
public class CategoriesAPI {
	@Autowired
	private CategoriesService categoriesService;

	@GetMapping("/Categories")
	public ResponseEntity<List<Categories>> getAllCategoriesApi() {
		List<Categories> categories = categoriesService.categoriesFindAll();
		return ResponseEntity.ok(categories);
	}

	@GetMapping("/Categories/{id}")
	public ResponseEntity<Categories> getCategoriesByIdApi(@PathVariable int id) {
		Categories categories =(Categories)categoriesService.categoriesFindById(id).orElse(new Categories());
		return ResponseEntity.ok(categories);
	}
	@PostMapping("/Categories")
	public ResponseEntity<Categories> addNewCategories(@RequestBody Categories entity) {
	    Categories saved = categoriesService.categoriesSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Categories")
	public ResponseEntity<Categories> updateCategories(@RequestBody Categories entity) {
	    Categories updated = categoriesService.categoriesSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Categories/{id}")
	public ResponseEntity<Void> deleteCategories(@PathVariable int id) {
	    categoriesService.categoriesDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
