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
public class ShippingMethodsAPI {
	@Autowired
	private ShippingMethodsService shippingMethodsService;
	
	@Autowired
	private ShippingMethodsMapper shippingMethodsMapper;

	@GetMapping("/admin/ShippingMethods")
	public ResponseEntity<List<ShippingMethodsDTOS>> getAllShippingMethodsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<ShippingMethodsDTOS> shippingMethods = new ArrayList<ShippingMethodsDTOS>();
		shippingMethodsService.shippingMethodsFindAll(page, size).forEach(e -> {
			shippingMethods.add(shippingMethodsMapper.mapper(e));
		});
		return ResponseEntity.ok(shippingMethods);
	}

	@GetMapping("/admin/ShippingMethods/{id}")
	public ResponseEntity<ShippingMethods> getShippingMethodsByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		ShippingMethods shippingMethods =(ShippingMethods)shippingMethodsService.shippingMethodsFindById(id).orElse(new ShippingMethods());
		return ResponseEntity.ok(shippingMethods);
	}
	@PostMapping("/admin/ShippingMethods")
	public ResponseEntity<ShippingMethods> addNewShippingMethods(@RequestBody ShippingMethodsDTOS entity) {
	    
	    ShippingMethods saved = shippingMethodsService.shippingMethodsSave(shippingMethodsMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/ShippingMethods/{id}")
	public ResponseEntity<?> updateShippingMethods( @PathVariable int id,@RequestBody ShippingMethodsDTOS entity) {
			try {
			ShippingMethods  isExist = shippingMethodsService.shippingMethodsFindById(id).orElse(null);
			if (isExist != null) {
				ShippingMethods  update = shippingMethodsMapper.mapperToObject(entity);
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
