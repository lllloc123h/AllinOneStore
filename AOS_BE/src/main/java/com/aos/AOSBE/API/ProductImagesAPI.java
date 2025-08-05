package com.aos.AOSBE.API;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.aos.AOSBE.DTOS.ProductImagesDTOS;
import com.aos.AOSBE.Entity.ProductImages;
import com.aos.AOSBE.Mapper.ProductImagesMapper;
import com.aos.AOSBE.Service.ProductImagesService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductImagesAPI {
	@Autowired
	private ProductImagesService productImagesService;

	@Autowired
	private ProductImagesMapper productImagesMapper;

	@GetMapping("/admin/ProductImages")
	public ResponseEntity<?> getAllProductImagesApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<ProductImages> pageResult = productImagesService.productImagesFindAll(page, size, filters);
		List<ProductImagesDTOS> productImages = pageResult.getContent().stream().map(productImagesMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", productImages);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/admin/ProductImages/{id}")
	public ResponseEntity<ProductImages> getProductImagesByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		ProductImages productImages = (ProductImages) productImagesService.productImagesFindById(id)
				.orElse(new ProductImages());
		return ResponseEntity.ok(productImages);
	}

	@PostMapping("/admin/ProductImages")
	public ResponseEntity<ProductImages> addNewProductImages(@RequestBody ProductImagesDTOS entity) {

		ProductImages saved = productImagesService.productImagesSave(productImagesMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/ProductImages/{id}")
	public ResponseEntity<?> updateProductImages(@PathVariable int id, @RequestBody ProductImagesDTOS entity) {
		try {
			List<ProductImages> isExist = productImagesService.findByProductItemsId(id);
			String imgURL = "";
			if (isExist.size() != 0) {
				imgURL = isExist.get(0).getImageUrl();
			}
			if (imgURL != null && !imgURL.isEmpty()) {
				isExist.get(0).setImageUrl(entity.getImageUrl());
				productImagesService.productImagesSave(isExist.get(0));
				return ResponseEntity.ok(Map.of("measage", "Update successfuly", "update", isExist.get(0)));
			} else {
				ProductImages update = productImagesMapper.mapperToObject(entity);

				productImagesService.productImagesSave(update);
				return ResponseEntity.ok(Map.of("measage", "create successfuly", "update", isExist.get(0)));
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