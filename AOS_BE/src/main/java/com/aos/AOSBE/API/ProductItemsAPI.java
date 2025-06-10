package com.aos.AOSBE.API;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.aos.AOSBE.DTOS.ProductItemsDTOS;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Mapper.ProductItemsMapper;
import com.aos.AOSBE.Service.ProductItemsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductItemsAPI {
	@Autowired
	private ProductItemsService productItemsService;

	@Autowired
	private ProductItemsMapper productItemsMapper;

	@GetMapping("/admin/ProductItems")
	public ResponseEntity<List<ProductItemsDTOS>> getAllProductItemsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {

		List<ProductItemsDTOS> productItems = new ArrayList<ProductItemsDTOS>();
		productItemsService.productItemsFindAll(page, size).forEach(e -> {
			productItems.add(productItemsMapper.mapper(e));
		});
		return ResponseEntity.ok(productItems);
	}

	@GetMapping("/admin/ProductItems/{id}")
	public ResponseEntity<ProductItems> getProductItemsByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		ProductItems productItems = (ProductItems) productItemsService.productItemsFindById(id)
				.orElse(new ProductItems());
		return ResponseEntity.ok(productItems);
	}

	@PostMapping("/admin/ProductItems")
	public ResponseEntity<ProductItems> addNewProductItems(@RequestBody ProductItemsDTOS entity) {

		ProductItems saved = productItemsService.productItemsSave(productItemsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/ProductItems")
	public ResponseEntity<ProductItems> updateProductItems(@RequestBody ProductItems entity) {
		ProductItems updated = productItemsService.productItemsSave(entity);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/admin/ProductItems/{id}")
	public ResponseEntity<Void> deleteProductItems(@PathVariable int id) {
		productItemsService.productItemsDeleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/Product/{sku}")
	public ResponseEntity<?> getAllProductItemsHaveSkuLikeApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @PathVariable String sku) {

		try {
			List<ProductItemsDTOS> productItems = new ArrayList<ProductItemsDTOS>();
			productItemsService.productItemsFindAllHaveSkuLike(sku).forEach(e -> {
				productItems.add(productItemsMapper.mapper(e));
			});
			return ResponseEntity.ok(productItems);
		} catch (Exception e) {

			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}

	}

}
