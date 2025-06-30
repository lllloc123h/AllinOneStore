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
public class CustomsAPI {
	@Autowired
	private CustomsService customsService;
	
	@Autowired
	private CustomsMapper customsMapper;

	@GetMapping("/admin/Customs")
	public ResponseEntity<List<CustomsDTOS>> getAllCustomsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<CustomsDTOS> customs = new ArrayList<CustomsDTOS>();
		customsService.customsFindAll(page, size).forEach(e -> {
			customs.add(customsMapper.mapper(e));
		});
		return ResponseEntity.ok(customs);
	}

	@GetMapping("/admin/Customs/{id}")
	public ResponseEntity<Customs> getCustomsByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		Customs customs =(Customs)customsService.customsFindById(id).orElse(new Customs());
		return ResponseEntity.ok(customs);
	}
	@PostMapping("/admin/Customs")
	public ResponseEntity<Customs> addNewCustoms(@RequestBody CustomsDTOS entity) {
	    
	    Customs saved = customsService.customsSave(customsMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/Customs/{id}")
	public ResponseEntity<?> updateCustoms( @PathVariable int id,@RequestBody CustomsDTOS entity) {
			try {
			Customs  isExist = customsService.customsFindById(id).orElse(null);
			if (isExist != null) {
				Customs  update = customsMapper.mapperToObject(entity);
				customsService.customsSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		} 
	}
	@DeleteMapping("/admin/Customs/{id}")
	public ResponseEntity<Void> deleteCustoms(@PathVariable int id) {
	    customsService.customsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
