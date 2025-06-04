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
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class CancelsAPI {
	@Autowired
	private CancelsService cancelsService;
	
	@Autowired
	private CancelsMapper cancelsMapper;

	@GetMapping("/Cancels")
	public ResponseEntity<List<CancelsDTOS>> getAllCancelsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<CancelsDTOS> cancels = new ArrayList<CancelsDTOS>();
		cancelsService.cancelsFindAll(page, size).forEach(e -> {
			cancels.add(cancelsMapper.mapper(e));
		});
		return ResponseEntity.ok(cancels);
	}

	@GetMapping("/Cancels/{id}")
	public ResponseEntity<Cancels> getCancelsByIdApi(@PathVariable int id) {
		Cancels cancels =(Cancels)cancelsService.cancelsFindById(id).orElse(new Cancels());
		return ResponseEntity.ok(cancels);
	}
	@PostMapping("/Cancels")
	public ResponseEntity<Cancels> addNewCancels(@RequestBody CancelsDTOS entity) {
	    
	    Cancels saved = cancelsService.cancelsSave(cancelsMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Cancels")
	public ResponseEntity<Cancels> updateCancels(@RequestBody Cancels entity) {
	    Cancels updated = cancelsService.cancelsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Cancels/{id}")
	public ResponseEntity<Void> deleteCancels(@PathVariable int id) {
	    cancelsService.cancelsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
