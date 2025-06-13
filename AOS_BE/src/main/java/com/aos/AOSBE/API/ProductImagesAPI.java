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
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductImagesAPI {
	@Autowired
	private ProductImagesService productImagesService;
	
	@Autowired
	private ProductImagesMapper productImagesMapper;

	@GetMapping("/admin/ProductImages")
	public ResponseEntity<List<ProductImagesDTOS>> getAllProductImagesApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<ProductImagesDTOS> productImages = new ArrayList<ProductImagesDTOS>();
		productImagesService.productImagesFindAll(page, size).forEach(e -> {
			productImages.add(productImagesMapper.mapper(e));
		});
		return ResponseEntity.ok(productImages);
	}

	@GetMapping("/admin/ProductImages/{id}")
	public ResponseEntity<ProductImages> getProductImagesByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		ProductImages productImages =(ProductImages)productImagesService.productImagesFindById(id).orElse(new ProductImages());
		return ResponseEntity.ok(productImages);
	}
	@PostMapping("/admin/ProductImages")
	public ResponseEntity<ProductImages> addNewProductImages(@RequestBody ProductImagesDTOS entity) {
	    
	    ProductImages saved = productImagesService.productImagesSave(productImagesMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/ProductImages")
	public ResponseEntity<?> updateProductImages(@RequestBody ProductImages entity) {
			try {
			ProductImages  isExist = productImagesService.productImagesFindById(id).orElse(null);
			if (isExist != null) {
				ProductImages  update = productImagesMapper.mapperToObject(entity);
				productImagesService.productImagesSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		} 
	}
	@DeleteMapping("/admin/ProductImages/{id}")
	public ResponseEntity<Void> deleteProductImages(@PathVariable int id) {
	    productImagesService.productImagesDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
