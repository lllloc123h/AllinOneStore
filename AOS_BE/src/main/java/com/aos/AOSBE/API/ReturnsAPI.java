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
public class ReturnsAPI {
	@Autowired
	private ReturnsService returnsService;
	private ReturnsMapper returnsMapper=new ReturnsMapper();

	@GetMapping("/Returns")
	public ResponseEntity<List<ReturnsDTOS>> getAllReturnsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<ReturnsDTOS> returns = new ArrayList<ReturnsDTOS>();
		returnsService.returnsFindAll(page, size).forEach(e -> {
			returns.add(returnsMapper.mapper(e));
		});
		return ResponseEntity.ok(returns);
	}

	@GetMapping("/Returns/{id}")
	public ResponseEntity<Returns> getReturnsByIdApi(@PathVariable int id) {
		Returns returns =(Returns)returnsService.returnsFindById(id).orElse(new Returns());
		return ResponseEntity.ok(returns);
	}
	@PostMapping("/Returns")
	public ResponseEntity<Returns> addNewReturns(@RequestBody ReturnsDTOS entity) {
	    
	    Returns saved = returnsService.returnsSave(returnsMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Returns")
	public ResponseEntity<Returns> updateReturns(@RequestBody Returns entity) {
	    Returns updated = returnsService.returnsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Returns/{id}")
	public ResponseEntity<Void> deleteReturns(@PathVariable int id) {
	    returnsService.returnsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
