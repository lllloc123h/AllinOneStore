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

import com.aos.AOSBE.DTOS.ShippingMethodsDTOS;
import com.aos.AOSBE.Entity.ShippingMethods;
import com.aos.AOSBE.Mapper.ShippingMethodsMapper;
import com.aos.AOSBE.Service.ShippingMethodsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ShippingMethodsAPI {
	@Autowired
	private ShippingMethodsService shippingMethodsService;

	@Autowired
	private ShippingMethodsMapper shippingMethodsMapper;

	@GetMapping("/admin/ShippingMethods")
	public ResponseEntity<?> getAllShippingMethodsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<ShippingMethods> pageResult = shippingMethodsService.shippingMethodsFindAll(page, size, filters);
		List<ShippingMethodsDTOS> shippingMethods = pageResult.getContent().stream().map(shippingMethodsMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", shippingMethods);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/admin/ShippingMethods/{id}")
	public ResponseEntity<ShippingMethods> getShippingMethodsByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		ShippingMethods shippingMethods = (ShippingMethods) shippingMethodsService.shippingMethodsFindById(id)
				.orElse(new ShippingMethods());
		return ResponseEntity.ok(shippingMethods);
	}

	@PostMapping("/admin/ShippingMethods")
	public ResponseEntity<ShippingMethods> addNewShippingMethods(@RequestBody ShippingMethodsDTOS entity) {

		ShippingMethods saved = shippingMethodsService
				.shippingMethodsSave(shippingMethodsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/ShippingMethods/{id}")
	public ResponseEntity<?> updateShippingMethods(@PathVariable int id, @RequestBody ShippingMethodsDTOS entity) {
		try {
			ShippingMethods isExist = shippingMethodsService.shippingMethodsFindById(id).orElse(null);
			if (isExist != null) {
				ShippingMethods update = shippingMethodsMapper.mapperToObject(entity);
				shippingMethodsService.shippingMethodsSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@DeleteMapping("/admin/ShippingMethods/{id}")
	public ResponseEntity<Void> deleteShippingMethods(@PathVariable int id) {
		shippingMethodsService.shippingMethodsDeleteById(id);
		return ResponseEntity.noContent().build();
	}

}