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
public class ProductImagesAPI {
	@Autowired
	private ProductImagesService productImagesService;
	private ProductImagesMapper productImagesMapper=new ProductImagesMapper();

	@GetMapping("/ProductImages")
	public ResponseEntity<List<ProductImagesDTOS>> getAllProductImagesApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<ProductImagesDTOS> productImages = new ArrayList<ProductImagesDTOS>();
		productImagesService.productImagesFindAll(page, size).forEach(e -> {
			productImages.add(productImagesMapper.mapper(e));
		});
		return ResponseEntity.ok(productImages);
	}

	@GetMapping("/ProductImages/{id}")
	public ResponseEntity<ProductImages> getProductImagesByIdApi(@PathVariable int id) {
		ProductImages productImages =(ProductImages)productImagesService.productImagesFindById(id).orElse(new ProductImages());
		return ResponseEntity.ok(productImages);
	}
	@PostMapping("/ProductImages")
	public ResponseEntity<ProductImages> addNewProductImages(@RequestBody ProductImagesDTOS entity) {
	    
	    ProductImages saved = productImagesService.productImagesSave(productImagesMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/ProductImages")
	public ResponseEntity<ProductImages> updateProductImages(@RequestBody ProductImages entity) {
	    ProductImages updated = productImagesService.productImagesSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/ProductImages/{id}")
	public ResponseEntity<Void> deleteProductImages(@PathVariable int id) {
	    productImagesService.productImagesDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
