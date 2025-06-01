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
public class BaseProductsAPI {
	@Autowired
	private BaseProductsService baseProductsService;
	private BaseProductsMapper baseProductsMapper=new BaseProductsMapper();

	@GetMapping("/BaseProducts")
	public ResponseEntity<List<BaseProductsDTOS>> getAllBaseProductsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<BaseProductsDTOS> baseProducts = new ArrayList<BaseProductsDTOS>();
		baseProductsService.baseProductsFindAll(page, size).forEach(e -> {
			baseProducts.add(baseProductsMapper.mapper(e));
		});
		return ResponseEntity.ok(baseProducts);
	}

	@GetMapping("/BaseProducts/{id}")
	public ResponseEntity<BaseProducts> getBaseProductsByIdApi(@PathVariable int id) {
		BaseProducts baseProducts =(BaseProducts)baseProductsService.baseProductsFindById(id).orElse(new BaseProducts());
		return ResponseEntity.ok(baseProducts);
	}
	@PostMapping("/BaseProducts")
	public ResponseEntity<BaseProducts> addNewBaseProducts(@RequestBody BaseProductsDTOS entity) {
	    
	    BaseProducts saved = baseProductsService.baseProductsSave(baseProductsMapper.mapperToObject(entity));	    
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
