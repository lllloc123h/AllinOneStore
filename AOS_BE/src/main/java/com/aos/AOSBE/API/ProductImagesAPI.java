package com.aos.AOSBE.API;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.aos.AOSBE.Service.QdrantService;
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
private QdrantService qdrantService;
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
	@GetMapping("/ProductItems/image/default/{productItemId}")
	public ResponseEntity<?> getAllProductImagesApi(@PathVariable int productItemId) {
		List<ProductImages> productImages = productImagesService.checkContainDefaultImagesByProductItemId(productItemId);
		List<ProductImagesDTOS> productImagesDTOS = productImages.stream().map(productImagesMapper::mapper).collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", productImagesDTOS);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/admin/ProductImages/{id}")
	public ResponseEntity<ProductImages> getProductImagesByIdApi(@PathVariable int id) {
		ProductImages productImages = (ProductImages) productImagesService.productImagesFindById(id)
				.orElse(new ProductImages());
		return ResponseEntity.ok(productImages);
	}

	@PostMapping("/admin/ProductImages")
	public ResponseEntity<?> addNewProductImages(@RequestBody ProductImagesDTOS entity) {

		try {
			List<ProductImages> isExist = productImagesService
					.checkContainDefaultImagesByProductItemId(entity.getProductItems());
			ProductImages mapped = productImagesMapper.mapperToObject(entity);
			if (isExist.size() <= 0) {
				mapped.setDefault(true);
			} else {
				mapped.setDefault(false);
			}
			mapped.setId(null);
			ProductImages saved = productImagesService.productImagesSave(mapped);
			return ResponseEntity.ok(saved);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@PutMapping("/admin/ProductImages/{id}")
	public ResponseEntity<?> updateProductImages(@PathVariable int id) {
		try {
			ProductImages address = productImagesService.productImagesFindById(id).orElse(null);
			if (address != null) {
				ProductImages updated = productImagesService.productImagesSetDefaultAddress(id, address);
//				qdrantService.setPayLoad(updated.getImageUrl(), updated.getProductItems().getSearchPoint().toString());
				// Cập nhật lại thông tin trong Qdrant
//				qdrantService.updateProductPayloadByProductItemId(updated.getProductItems().getId(), Map.of("imageUrl", updated.getImageUrl()));
				return ResponseEntity.ok().body(Map.of("message", "Set as default successfully","itemId",updated.getProductItems().getId()));
			} else {
				return ResponseEntity.badRequest().body(Map.of("message", "Address not found"));
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