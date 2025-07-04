package com.aos.AOSBE.API;

import java.util.ArrayList;
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

import com.aos.AOSBE.DTOS.BaseProductsDTOS;
import com.aos.AOSBE.DTOS.FilterResponseDTOS;
import com.aos.AOSBE.Entity.BaseProducts;
import com.aos.AOSBE.Mapper.BaseProductsMapper;
import com.aos.AOSBE.Service.BaseProductsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class BaseProductsAPI {
	@Autowired
	private BaseProductsService baseProductsService;

	@Autowired
	private BaseProductsMapper baseProductsMapper;

	@GetMapping("/BaseProducts")
	public ResponseEntity<List<FilterResponseDTOS>> getAllBaseProductsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "9") int size, @RequestParam(defaultValue = "") String searchByKeyWord,
			@RequestParam(required = false) Integer searchByCategory) {

		int categoryId = (searchByCategory == null) ? -1 : searchByCategory;
		List<FilterResponseDTOS> FilterResponseMapped = new ArrayList<>();
		List<Object[]> FilterResponse = baseProductsService
				.baseProductsFindAllAsDto(page, size, searchByKeyWord, categoryId).getContent();
		FilterResponse.forEach(e -> {
			FilterResponseDTOS item = new FilterResponseDTOS();
			item.setId((int) e[0]);
			item.setName((String) e[1]);
			item.setMaterial((String) e[2]);
			item.setMainImage((String) e[3]);
			item.setCategoryId((Integer) e[4]);
			item.setCustom((Boolean) e[5]);
			item.setTurnBuy((Integer) e[6]);
			item.setRating((Integer) e[7]);
			item.setDescription((String) e[8]);
			item.setActive((Boolean) e[9]);
			item.setSafetyStock((Integer) e[10]);
			item.setQty(((Number) e[11]).intValue()); // handle BigInteger
			item.setListPriceRaw((String) e[12]);
			FilterResponseMapped.add(item);
		});
		return ResponseEntity.ok(FilterResponseMapped);
	}

	@GetMapping("/admin/BaseProducts")

	public ResponseEntity<?> getAllBaseProductsAdminRoleApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("search");
		filters.remove("size");
		Page<BaseProducts> pageResult = baseProductsService.baseProductsFindAll(page, size, filters);
		List<BaseProductsDTOS> baseProducts = pageResult.getContent().stream().map(baseProductsMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", baseProducts);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);

	}

	@GetMapping("/admin/BaseProducts/{id}")
	public ResponseEntity<BaseProducts> getAllBaseProductsByIdAdminRoleApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		BaseProducts baseProducts = (BaseProducts) baseProductsService.baseProductsFindById(id)
				.orElse(new BaseProducts());
		return ResponseEntity.ok(baseProducts);
	}

	@GetMapping("/BaseProducts/{id}")
	public ResponseEntity<BaseProducts> getBaseProductsByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		BaseProducts baseProducts = (BaseProducts) baseProductsService.baseProductsFindById(id)
				.orElse(new BaseProducts());
		return ResponseEntity.ok(baseProducts);
	}

	@PostMapping("/admin/BaseProducts")
	public ResponseEntity<BaseProducts> addNewBaseProducts(@RequestBody BaseProductsDTOS entity) {

		BaseProducts saved = baseProductsService.baseProductsSave(baseProductsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/BaseProducts/{id}")
	public ResponseEntity<?> updateBaseProducts(@PathVariable int id, @RequestBody BaseProductsDTOS entity) {
		try {
			BaseProducts isExist = baseProductsService.baseProductsFindById(id).orElse(null);
			if (isExist != null) {
				entity.setId(isExist.getId());
				BaseProducts updated = baseProductsMapper.mapperToObject(entity);
				return ResponseEntity.ok().body(Map.of("measage", "Update successfuly", "update", updated));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
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