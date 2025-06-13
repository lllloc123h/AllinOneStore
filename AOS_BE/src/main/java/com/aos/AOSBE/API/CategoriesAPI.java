package com.aos.AOSBE.API;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
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
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoriesAPI {
	@Autowired
	private CategoriesService categoriesService;
	
	@Autowired
	private CategoriesMapper categoriesMapper;

	@GetMapping("/admin/Categories")
	public ResponseEntity<List<CategoriesDTOS>> getAllCategoriesApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<CategoriesDTOS> categories = new ArrayList<CategoriesDTOS>();
		categoriesService.categoriesFindAll(page, size).forEach(e -> {
			categories.add(categoriesMapper.mapper(e));
		});
		return ResponseEntity.ok(categories);
	}

	@GetMapping("/admin/Categories/{id}")
	public ResponseEntity<Categories> getCategoriesByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		Categories categories =(Categories)categoriesService.categoriesFindById(id).orElse(new Categories());
		return ResponseEntity.ok(categories);
	}
	@PostMapping("/admin/Categories")
	public ResponseEntity<Categories> addNewCategories(@RequestBody CategoriesDTOS entity) {
	    
	    Categories saved = categoriesService.categoriesSave(categoriesMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/Categories/{id}")
	public ResponseEntity<?> updateCategories( @PathVariable int id,@RequestBody CategoriesDTOS entity) {
			try {
			Categories  isExist = categoriesService.categoriesFindById(id).orElse(null);
			if (isExist != null) {
				Categories  update = categoriesMapper.mapperToObject(entity);
				categoriesService.categoriesSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		} 
	}
	@DeleteMapping("/admin/Categories/{id}")
	public ResponseEntity<Void> deleteCategories(@PathVariable int id) {
	    categoriesService.categoriesDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
