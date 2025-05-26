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
public class ProductItemsAPI {
	@Autowired
	private ProductItemsService productItemsService;

	@GetMapping("/ProductItems")
	public ResponseEntity<List<ProductItems>> getAllProductItemsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<ProductItems> productItems = productItemsService.productItemsFindAll(page,size);
		return ResponseEntity.ok(productItems);
	}

	@GetMapping("/ProductItems/{id}")
	public ResponseEntity<ProductItems> getProductItemsByIdApi(@PathVariable int id) {
		ProductItems productItems =(ProductItems)productItemsService.productItemsFindById(id).orElse(new ProductItems());
		return ResponseEntity.ok(productItems);
	}
	@PostMapping("/ProductItems")
	public ResponseEntity<ProductItems> addNewProductItems(@RequestBody ProductItems entity) {
	    ProductItems saved = productItemsService.productItemsSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/ProductItems")
	public ResponseEntity<ProductItems> updateProductItems(@RequestBody ProductItems entity) {
	    ProductItems updated = productItemsService.productItemsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/ProductItems/{id}")
	public ResponseEntity<Void> deleteProductItems(@PathVariable int id) {
	    productItemsService.productItemsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
