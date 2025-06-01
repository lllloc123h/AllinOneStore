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
public class CustomAPI {
	@Autowired
	private CustomService customService;
	private CustomMapper customMapper=new CustomMapper();

	@GetMapping("/Custom")
	public ResponseEntity<List<CustomDTOS>> getAllCustomApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<CustomDTOS> custom = new ArrayList<CustomDTOS>();
		customService.customFindAll(page, size).forEach(e -> {
			custom.add(customMapper.mapper(e));
		});
		return ResponseEntity.ok(custom);
	}

	@GetMapping("/Custom/{id}")
	public ResponseEntity<Custom> getCustomByIdApi(@PathVariable int id) {
		Custom custom =(Custom)customService.customFindById(id).orElse(new Custom());
		return ResponseEntity.ok(custom);
	}
	@PostMapping("/Custom")
	public ResponseEntity<Custom> addNewCustom(@RequestBody CustomDTOS entity) {
	    
	    Custom saved = customService.customSave(customMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Custom")
	public ResponseEntity<Custom> updateCustom(@RequestBody Custom entity) {
	    Custom updated = customService.customSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Custom/{id}")
	public ResponseEntity<Void> deleteCustom(@PathVariable int id) {
	    customService.customDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
