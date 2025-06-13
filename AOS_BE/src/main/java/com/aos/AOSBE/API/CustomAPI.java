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
public class CustomAPI {
	@Autowired
	private CustomService customService;
	
	@Autowired
	private CustomMapper customMapper;

	@GetMapping("/admin/Custom")
	public ResponseEntity<List<CustomDTOS>> getAllCustomApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<CustomDTOS> custom = new ArrayList<CustomDTOS>();
		customService.customFindAll(page, size).forEach(e -> {
			custom.add(customMapper.mapper(e));
		});
		return ResponseEntity.ok(custom);
	}

	@GetMapping("/admin/Custom/{id}")
	public ResponseEntity<Custom> getCustomByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		Custom custom =(Custom)customService.customFindById(id).orElse(new Custom());
		return ResponseEntity.ok(custom);
	}
	@PostMapping("/admin/Custom")
	public ResponseEntity<Custom> addNewCustom(@RequestBody CustomDTOS entity) {
	    
	    Custom saved = customService.customSave(customMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/Custom")
	public ResponseEntity<?> updateCustom(@RequestBody Custom entity) {
			try {
			Custom  isExist = customService.customFindById(id).orElse(null);
			if (isExist != null) {
				Custom  update = customMapper.mapperToObject(entity);
				customService.customSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		} 
	}
	@DeleteMapping("/admin/Custom/{id}")
	public ResponseEntity<Void> deleteCustom(@PathVariable int id) {
	    customService.customDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
