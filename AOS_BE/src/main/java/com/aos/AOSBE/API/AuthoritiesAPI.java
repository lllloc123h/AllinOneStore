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
public class AuthoritiesAPI {
	@Autowired
	private AuthoritiesService authoritiesService;
	private AuthoritiesMapper authoritiesMapper=new AuthoritiesMapper();

	@GetMapping("/Authorities")
	public ResponseEntity<List<AuthoritiesDTOS>> getAllAuthoritiesApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<AuthoritiesDTOS> authorities = new ArrayList<AuthoritiesDTOS>();
		authoritiesService.authoritiesFindAll(page, size).forEach(e -> {
			authorities.add(authoritiesMapper.mapper(e));
		});
		return ResponseEntity.ok(authorities);
	}

	@GetMapping("/Authorities/{id}")
	public ResponseEntity<Authorities> getAuthoritiesByIdApi(@PathVariable int id) {
		Authorities authorities =(Authorities)authoritiesService.authoritiesFindById(id).orElse(new Authorities());
		return ResponseEntity.ok(authorities);
	}
	@PostMapping("/Authorities")
	public ResponseEntity<Authorities> addNewAuthorities(@RequestBody Authorities entity) {
	    Authorities saved = authoritiesService.authoritiesSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Authorities")
	public ResponseEntity<Authorities> updateAuthorities(@RequestBody Authorities entity) {
	    Authorities updated = authoritiesService.authoritiesSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Authorities/{id}")
	public ResponseEntity<Void> deleteAuthorities(@PathVariable int id) {
	    authoritiesService.authoritiesDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
